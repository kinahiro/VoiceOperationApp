package com.main.voiceoperationapp.device;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.stream.JsonReader;
import com.main.voiceoperationapp.MainActivity;
import com.main.voiceoperationapp.config.DeviceRegisterConf;
import com.main.voiceoperationapp.exception.DeviceRegisterException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class DeviceRegister {

    private static String TAG = "VoiceOperationApp";

    // Configuration from typesafe
    private DeviceRegisterConf deviceRegisterConf;

    private DeviceModel deviceModel;

    private Device device;

    // The API interface (used by retrofit)
    private DeviceInterface deviceInterface;

    // The Gson object to read/write the device model and instance in a file
    private Gson gson;

    public static Response<DeviceModel> responseDeviceModel = null;

    public static Response<Device> responseDevice = null;

    Listener mListener;
    public void setListener(Listener listener) {
        mListener = listener;
    }

    public DeviceRegister(DeviceRegisterConf deviceRegisterConf, String accessToken) {
        this.deviceRegisterConf = deviceRegisterConf;

        gson = new Gson();

        // Add an interceptor to add our accessToken in the queries
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {
            Request newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + accessToken)
                    .build();
            return chain.proceed(newRequest);
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(deviceRegisterConf.getApiEndpoint())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        deviceInterface = retrofit.create(DeviceInterface.class);
    }

    public void register() throws DeviceRegisterException {
        String projectId = deviceRegisterConf.getProjectId();
        // Register the device model
        deviceModel = registerModel(projectId)
                .orElseThrow(() -> new DeviceRegisterException("Unable to register the device model"));
        // Now we can register the instance
        device = registerInstance(projectId, deviceModel.getDeviceModelId())
                .orElseThrow(() -> new DeviceRegisterException("Unable to register the device instance"));
    }

    public DeviceModel getDeviceModel() {
        return deviceModel;
    }

    public Device getDevice() {
        return device;
    }

    private Optional<DeviceModel> registerModel(String projectId) throws DeviceRegisterException {
        Optional<DeviceModel> optionalDeviceModel = readFromFile(deviceRegisterConf.getDeviceModelFilePath(), DeviceModel.class);
        if (optionalDeviceModel.isPresent()) {
            Log.i(TAG,"Got device model from file");
            return optionalDeviceModel;
        }

        // If we can't get the device model from a file, continue with the webservice
        String modelId = projectId + UUID.randomUUID();
        //String modelId = projectId;

        DeviceModel.Manifest manifest = new DeviceModel.Manifest();
        manifest.setManufacturer("developer");
        manifest.setProductName("assistant test");
        manifest.setDeviceDescription("VoiceOperationApp in Java");

        DeviceModel deviceModel = new DeviceModel();
        deviceModel.setDeviceModelId(modelId);
        deviceModel.setProjectId(projectId);
        deviceModel.setName(String.format("projects/%s/deviceModels/%s", projectId, modelId));
        // https://developers.google.com/assistant/sdk/reference/device-registration/model-and-instance-schemas#device_model_json
        // Light does not fit this project but there is nothing better in the API
        deviceModel.setDeviceType("action.devices.types.PHONE");
        deviceModel.setManifest(manifest);

        try {
            Log.i(TAG,"Creating device model");
            RegisterDeviceModelTask registerDeviceModelTask = new RegisterDeviceModelTask(deviceInterface, projectId, deviceModel);
            registerDeviceModelTask.execute();
            //Response<DeviceModel> response = deviceInterface.registerModel(projectId, deviceModel).execute();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (responseDeviceModel.isSuccessful() && responseDeviceModel.body() != null) {
                String filePath = deviceRegisterConf.getDeviceModelFilePath();
                filePath = mListener.createFile(filePath);
                // Save the device model in a file to not request the api each time we start the project
                try (FileWriter writer = new FileWriter(filePath)) {
                    gson.toJson(responseDeviceModel.body(), writer);
                }
                return Optional.of(responseDeviceModel.body());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeviceRegisterException("Error during registration of the device model", e);
        }
    }

    private Optional<Device> registerInstance(String projectId, String modelId) throws DeviceRegisterException {
        Optional<Device> optionalDevice = readFromFile(deviceRegisterConf.getDeviceInstanceFilePath(), Device.class);
        if (optionalDevice.isPresent()) {
            Log.i(TAG,"Got device instance from file");
            return optionalDevice;
        }

        Device device = new Device();
        device.setId(UUID.randomUUID().toString());
        device.setModelId(modelId);
        // Here we use the Google Assistant Service
        device.setClientType("SDK_SERVICE");

        try {
            Log.i(TAG,"Creating device instance");
            //Response<Device> response = deviceInterface.registerDevice(projectId, device).execute();
            RegisterDeviceTask registerDeviceTask = new RegisterDeviceTask(deviceInterface, projectId, device);
            registerDeviceTask.execute();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (responseDevice.isSuccessful() && responseDevice.body() != null) {
                String filePath = deviceRegisterConf.getDeviceInstanceFilePath();
                filePath = mListener.createFile(filePath);
                // Save the device instance in a file to not request the api each time we start the project
                try (FileWriter writer = new FileWriter(filePath)) {
                    gson.toJson(responseDevice.body(), writer);
                }
                return Optional.of(responseDevice.body());
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new DeviceRegisterException("Error during registration of the device instance", e);
        }
    }

    /**
     * Deserialize from json an object in a file
     *
     * @param filePath    the file in which we stored the object to deserialize
     * @param targetClass the target class for the deserialization
     * @param <T>         the type of the object to deserialize
     * @return an optional with the object deserialized if the process succeed
     */
    private <T> Optional<T> readFromFile(String filePath, Class<T> targetClass) {
        File file = new File(filePath);
        if (file.exists()) {
            try {
                return Optional.of(gson.fromJson(new JsonReader(new FileReader(file)), targetClass));
            } catch (IOException e) {
                Log.w(TAG,"Unable to read the content of the file : "+ e);
            }
        }
        return Optional.empty();
    }


    public interface Listener {
        String createFile(String fileName);
    }
}

class RegisterDeviceModelTask extends AsyncTask<String, Void, Response<DeviceModel>> {

    static String TAG = "VoiceOperationApp";

    private DeviceInterface deviceInterface;
    private String projectId;
    private DeviceModel deviceModel;

    public RegisterDeviceModelTask(DeviceInterface dInterface, String pId, DeviceModel model){
        deviceInterface = dInterface;
        projectId = pId;
        deviceModel = model;
    }

    protected Response<DeviceModel> doInBackground(String... codes) {
        try {
            DeviceRegister.responseDeviceModel
                    = deviceInterface.registerModel(projectId, deviceModel).execute();
            return DeviceRegister.responseDeviceModel;
        } catch (Exception ex)
        {
            Log.e(TAG,"Could not register DeviceModel : " + ex.toString());
            return null;
        }
    }

    protected void onPostExecute(Response<DeviceModel> result) {
    }
}

class RegisterDeviceTask extends AsyncTask<String, Void, Response<Device>> {

    static String TAG = "VoiceOperationApp";

    private DeviceInterface deviceInterface;
    private String projectId;
    private Device device;

    public RegisterDeviceTask(DeviceInterface dInterface, String pId, Device dev){
        deviceInterface = dInterface;
        projectId = pId;
        device = dev;
    }

    protected Response<Device> doInBackground(String... codes) {
        try {
            DeviceRegister.responseDevice
                    = deviceInterface.registerDevice(projectId, device).execute();
            return DeviceRegister.responseDevice;
        } catch (Exception ex)
        {
            Log.e(TAG,"Could not register Device : " + ex.toString());
            return null;
        }
    }

    protected void onPostExecute(Response<DeviceModel> result) {
    }
}