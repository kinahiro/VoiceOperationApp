// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: google/assistant/embedded/v1alpha2/embedded_assistant.proto

package com.google.assistant.embedded.v1alpha2;

/**
 * <pre>
 * The dialog state resulting from the user's query. Multiple of these messages
 * may be received.
 * </pre>
 *
 * Protobuf type {@code google.assistant.embedded.v1alpha2.DialogStateOut}
 */
public  final class DialogStateOut extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:google.assistant.embedded.v1alpha2.DialogStateOut)
    DialogStateOutOrBuilder {
private static final long serialVersionUID = 0L;
  // Use DialogStateOut.newBuilder() to construct.
  private DialogStateOut(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private DialogStateOut() {
    supplementalDisplayText_ = "";
    conversationState_ = com.google.protobuf.ByteString.EMPTY;
    microphoneMode_ = 0;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new DialogStateOut();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  private DialogStateOut(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            java.lang.String s = input.readStringRequireUtf8();

            supplementalDisplayText_ = s;
            break;
          }
          case 18: {

            conversationState_ = input.readBytes();
            break;
          }
          case 24: {
            int rawValue = input.readEnum();

            microphoneMode_ = rawValue;
            break;
          }
          case 32: {

            volumePercentage_ = input.readInt32();
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.google.assistant.embedded.v1alpha2.AssistantProto.internal_static_google_assistant_embedded_v1alpha2_DialogStateOut_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.google.assistant.embedded.v1alpha2.AssistantProto.internal_static_google_assistant_embedded_v1alpha2_DialogStateOut_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.google.assistant.embedded.v1alpha2.DialogStateOut.class, com.google.assistant.embedded.v1alpha2.DialogStateOut.Builder.class);
  }

  /**
   * <pre>
   * Possible states of the microphone after a `Assist` RPC completes.
   * </pre>
   *
   * Protobuf enum {@code google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode}
   */
  public enum MicrophoneMode
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <pre>
     * No mode specified.
     * </pre>
     *
     * <code>MICROPHONE_MODE_UNSPECIFIED = 0;</code>
     */
    MICROPHONE_MODE_UNSPECIFIED(0),
    /**
     * <pre>
     * The service is not expecting a follow-on question from the user.
     * The microphone should remain off until the user re-activates it.
     * </pre>
     *
     * <code>CLOSE_MICROPHONE = 1;</code>
     */
    CLOSE_MICROPHONE(1),
    /**
     * <pre>
     * The service is expecting a follow-on question from the user. The
     * microphone should be re-opened when the `AudioOut` playback completes
     * (by starting a new `Assist` RPC call to send the new audio).
     * </pre>
     *
     * <code>DIALOG_FOLLOW_ON = 2;</code>
     */
    DIALOG_FOLLOW_ON(2),
    UNRECOGNIZED(-1),
    ;

    /**
     * <pre>
     * No mode specified.
     * </pre>
     *
     * <code>MICROPHONE_MODE_UNSPECIFIED = 0;</code>
     */
    public static final int MICROPHONE_MODE_UNSPECIFIED_VALUE = 0;
    /**
     * <pre>
     * The service is not expecting a follow-on question from the user.
     * The microphone should remain off until the user re-activates it.
     * </pre>
     *
     * <code>CLOSE_MICROPHONE = 1;</code>
     */
    public static final int CLOSE_MICROPHONE_VALUE = 1;
    /**
     * <pre>
     * The service is expecting a follow-on question from the user. The
     * microphone should be re-opened when the `AudioOut` playback completes
     * (by starting a new `Assist` RPC call to send the new audio).
     * </pre>
     *
     * <code>DIALOG_FOLLOW_ON = 2;</code>
     */
    public static final int DIALOG_FOLLOW_ON_VALUE = 2;


    public final int getNumber() {
      if (this == UNRECOGNIZED) {
        throw new java.lang.IllegalArgumentException(
            "Can't get the number of an unknown enum value.");
      }
      return value;
    }

    /**
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static MicrophoneMode valueOf(int value) {
      return forNumber(value);
    }

    public static MicrophoneMode forNumber(int value) {
      switch (value) {
        case 0: return MICROPHONE_MODE_UNSPECIFIED;
        case 1: return CLOSE_MICROPHONE;
        case 2: return DIALOG_FOLLOW_ON;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<MicrophoneMode>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static final com.google.protobuf.Internal.EnumLiteMap<
        MicrophoneMode> internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<MicrophoneMode>() {
            public MicrophoneMode findValueByNumber(int number) {
              return MicrophoneMode.forNumber(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(ordinal());
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return com.google.assistant.embedded.v1alpha2.DialogStateOut.getDescriptor().getEnumTypes().get(0);
    }

    private static final MicrophoneMode[] VALUES = values();

    public static MicrophoneMode valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      if (desc.getIndex() == -1) {
        return UNRECOGNIZED;
      }
      return VALUES[desc.getIndex()];
    }

    private final int value;

    private MicrophoneMode(int value) {
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode)
  }

  public static final int SUPPLEMENTAL_DISPLAY_TEXT_FIELD_NUMBER = 1;
  private volatile java.lang.Object supplementalDisplayText_;
  /**
   * <pre>
   * *Output-only* Supplemental display text from the Assistant. This could be
   * the same as the speech spoken in `AssistResponse.audio_out` or it could
   * be some additional information which aids the user's understanding.
   * </pre>
   *
   * <code>string supplemental_display_text = 1;</code>
   */
  public java.lang.String getSupplementalDisplayText() {
    java.lang.Object ref = supplementalDisplayText_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      supplementalDisplayText_ = s;
      return s;
    }
  }
  /**
   * <pre>
   * *Output-only* Supplemental display text from the Assistant. This could be
   * the same as the speech spoken in `AssistResponse.audio_out` or it could
   * be some additional information which aids the user's understanding.
   * </pre>
   *
   * <code>string supplemental_display_text = 1;</code>
   */
  public com.google.protobuf.ByteString
      getSupplementalDisplayTextBytes() {
    java.lang.Object ref = supplementalDisplayText_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      supplementalDisplayText_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int CONVERSATION_STATE_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString conversationState_;
  /**
   * <pre>
   * *Output-only* State information for the subsequent `Assist` RPC. This
   * value should be saved in the client and returned in the
   * [`DialogStateIn.conversation_state`](#dialogstatein) field with the next
   * `Assist` RPC. (The client does not need to interpret or otherwise use this
   * value.) This information should be saved across device reboots. However,
   * this value should be cleared (not saved in the client) during a
   * factory-default reset.
   * </pre>
   *
   * <code>bytes conversation_state = 2;</code>
   */
  public com.google.protobuf.ByteString getConversationState() {
    return conversationState_;
  }

  public static final int MICROPHONE_MODE_FIELD_NUMBER = 3;
  private int microphoneMode_;
  /**
   * <pre>
   * *Output-only* Specifies the mode of the microphone after this `Assist`
   * RPC is processed.
   * </pre>
   *
   * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
   */
  public int getMicrophoneModeValue() {
    return microphoneMode_;
  }
  /**
   * <pre>
   * *Output-only* Specifies the mode of the microphone after this `Assist`
   * RPC is processed.
   * </pre>
   *
   * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
   */
  public com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode getMicrophoneMode() {
    @SuppressWarnings("deprecation")
    com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode result = com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode.valueOf(microphoneMode_);
    return result == null ? com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode.UNRECOGNIZED : result;
  }

  public static final int VOLUME_PERCENTAGE_FIELD_NUMBER = 4;
  private int volumePercentage_;
  /**
   * <pre>
   * *Output-only* Updated volume level. The value will be 0 or omitted
   * (indicating no change) unless a voice command such as *Increase the volume*
   * or *Set volume level 4* was recognized, in which case the value will be
   * between 1 and 100 (corresponding to the new volume level of 1% to 100%).
   * Typically, a client should use this volume level when playing the
   * `audio_out` data, and retain this value as the current volume level and
   * supply it in the `AudioOutConfig` of the next `AssistRequest`. (Some
   * clients may also implement other ways to allow the current volume level to
   * be changed, for example, by providing a knob that the user can turn.)
   * </pre>
   *
   * <code>int32 volume_percentage = 4;</code>
   */
  public int getVolumePercentage() {
    return volumePercentage_;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (!getSupplementalDisplayTextBytes().isEmpty()) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, supplementalDisplayText_);
    }
    if (!conversationState_.isEmpty()) {
      output.writeBytes(2, conversationState_);
    }
    if (microphoneMode_ != com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode.MICROPHONE_MODE_UNSPECIFIED.getNumber()) {
      output.writeEnum(3, microphoneMode_);
    }
    if (volumePercentage_ != 0) {
      output.writeInt32(4, volumePercentage_);
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!getSupplementalDisplayTextBytes().isEmpty()) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, supplementalDisplayText_);
    }
    if (!conversationState_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, conversationState_);
    }
    if (microphoneMode_ != com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode.MICROPHONE_MODE_UNSPECIFIED.getNumber()) {
      size += com.google.protobuf.CodedOutputStream
        .computeEnumSize(3, microphoneMode_);
    }
    if (volumePercentage_ != 0) {
      size += com.google.protobuf.CodedOutputStream
        .computeInt32Size(4, volumePercentage_);
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.google.assistant.embedded.v1alpha2.DialogStateOut)) {
      return super.equals(obj);
    }
    com.google.assistant.embedded.v1alpha2.DialogStateOut other = (com.google.assistant.embedded.v1alpha2.DialogStateOut) obj;

    if (!getSupplementalDisplayText()
        .equals(other.getSupplementalDisplayText())) return false;
    if (!getConversationState()
        .equals(other.getConversationState())) return false;
    if (microphoneMode_ != other.microphoneMode_) return false;
    if (getVolumePercentage()
        != other.getVolumePercentage()) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    hash = (37 * hash) + SUPPLEMENTAL_DISPLAY_TEXT_FIELD_NUMBER;
    hash = (53 * hash) + getSupplementalDisplayText().hashCode();
    hash = (37 * hash) + CONVERSATION_STATE_FIELD_NUMBER;
    hash = (53 * hash) + getConversationState().hashCode();
    hash = (37 * hash) + MICROPHONE_MODE_FIELD_NUMBER;
    hash = (53 * hash) + microphoneMode_;
    hash = (37 * hash) + VOLUME_PERCENTAGE_FIELD_NUMBER;
    hash = (53 * hash) + getVolumePercentage();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.google.assistant.embedded.v1alpha2.DialogStateOut parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(com.google.assistant.embedded.v1alpha2.DialogStateOut prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * <pre>
   * The dialog state resulting from the user's query. Multiple of these messages
   * may be received.
   * </pre>
   *
   * Protobuf type {@code google.assistant.embedded.v1alpha2.DialogStateOut}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:google.assistant.embedded.v1alpha2.DialogStateOut)
      com.google.assistant.embedded.v1alpha2.DialogStateOutOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.google.assistant.embedded.v1alpha2.AssistantProto.internal_static_google_assistant_embedded_v1alpha2_DialogStateOut_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.google.assistant.embedded.v1alpha2.AssistantProto.internal_static_google_assistant_embedded_v1alpha2_DialogStateOut_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.google.assistant.embedded.v1alpha2.DialogStateOut.class, com.google.assistant.embedded.v1alpha2.DialogStateOut.Builder.class);
    }

    // Construct using com.google.assistant.embedded.v1alpha2.DialogStateOut.newBuilder()
    private Builder() {
      maybeForceBuilderInitialization();
    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);
      maybeForceBuilderInitialization();
    }
    private void maybeForceBuilderInitialization() {
      if (com.google.protobuf.GeneratedMessageV3
              .alwaysUseFieldBuilders) {
      }
    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      supplementalDisplayText_ = "";

      conversationState_ = com.google.protobuf.ByteString.EMPTY;

      microphoneMode_ = 0;

      volumePercentage_ = 0;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return com.google.assistant.embedded.v1alpha2.AssistantProto.internal_static_google_assistant_embedded_v1alpha2_DialogStateOut_descriptor;
    }

    @java.lang.Override
    public com.google.assistant.embedded.v1alpha2.DialogStateOut getDefaultInstanceForType() {
      return com.google.assistant.embedded.v1alpha2.DialogStateOut.getDefaultInstance();
    }

    @java.lang.Override
    public com.google.assistant.embedded.v1alpha2.DialogStateOut build() {
      com.google.assistant.embedded.v1alpha2.DialogStateOut result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public com.google.assistant.embedded.v1alpha2.DialogStateOut buildPartial() {
      com.google.assistant.embedded.v1alpha2.DialogStateOut result = new com.google.assistant.embedded.v1alpha2.DialogStateOut(this);
      result.supplementalDisplayText_ = supplementalDisplayText_;
      result.conversationState_ = conversationState_;
      result.microphoneMode_ = microphoneMode_;
      result.volumePercentage_ = volumePercentage_;
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof com.google.assistant.embedded.v1alpha2.DialogStateOut) {
        return mergeFrom((com.google.assistant.embedded.v1alpha2.DialogStateOut)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(com.google.assistant.embedded.v1alpha2.DialogStateOut other) {
      if (other == com.google.assistant.embedded.v1alpha2.DialogStateOut.getDefaultInstance()) return this;
      if (!other.getSupplementalDisplayText().isEmpty()) {
        supplementalDisplayText_ = other.supplementalDisplayText_;
        onChanged();
      }
      if (other.getConversationState() != com.google.protobuf.ByteString.EMPTY) {
        setConversationState(other.getConversationState());
      }
      if (other.microphoneMode_ != 0) {
        setMicrophoneModeValue(other.getMicrophoneModeValue());
      }
      if (other.getVolumePercentage() != 0) {
        setVolumePercentage(other.getVolumePercentage());
      }
      this.mergeUnknownFields(other.unknownFields);
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      com.google.assistant.embedded.v1alpha2.DialogStateOut parsedMessage = null;
      try {
        parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        parsedMessage = (com.google.assistant.embedded.v1alpha2.DialogStateOut) e.getUnfinishedMessage();
        throw e.unwrapIOException();
      } finally {
        if (parsedMessage != null) {
          mergeFrom(parsedMessage);
        }
      }
      return this;
    }

    private java.lang.Object supplementalDisplayText_ = "";
    /**
     * <pre>
     * *Output-only* Supplemental display text from the Assistant. This could be
     * the same as the speech spoken in `AssistResponse.audio_out` or it could
     * be some additional information which aids the user's understanding.
     * </pre>
     *
     * <code>string supplemental_display_text = 1;</code>
     */
    public java.lang.String getSupplementalDisplayText() {
      java.lang.Object ref = supplementalDisplayText_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        supplementalDisplayText_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <pre>
     * *Output-only* Supplemental display text from the Assistant. This could be
     * the same as the speech spoken in `AssistResponse.audio_out` or it could
     * be some additional information which aids the user's understanding.
     * </pre>
     *
     * <code>string supplemental_display_text = 1;</code>
     */
    public com.google.protobuf.ByteString
        getSupplementalDisplayTextBytes() {
      java.lang.Object ref = supplementalDisplayText_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        supplementalDisplayText_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <pre>
     * *Output-only* Supplemental display text from the Assistant. This could be
     * the same as the speech spoken in `AssistResponse.audio_out` or it could
     * be some additional information which aids the user's understanding.
     * </pre>
     *
     * <code>string supplemental_display_text = 1;</code>
     */
    public Builder setSupplementalDisplayText(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      supplementalDisplayText_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * *Output-only* Supplemental display text from the Assistant. This could be
     * the same as the speech spoken in `AssistResponse.audio_out` or it could
     * be some additional information which aids the user's understanding.
     * </pre>
     *
     * <code>string supplemental_display_text = 1;</code>
     */
    public Builder clearSupplementalDisplayText() {
      
      supplementalDisplayText_ = getDefaultInstance().getSupplementalDisplayText();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * *Output-only* Supplemental display text from the Assistant. This could be
     * the same as the speech spoken in `AssistResponse.audio_out` or it could
     * be some additional information which aids the user's understanding.
     * </pre>
     *
     * <code>string supplemental_display_text = 1;</code>
     */
    public Builder setSupplementalDisplayTextBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      supplementalDisplayText_ = value;
      onChanged();
      return this;
    }

    private com.google.protobuf.ByteString conversationState_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <pre>
     * *Output-only* State information for the subsequent `Assist` RPC. This
     * value should be saved in the client and returned in the
     * [`DialogStateIn.conversation_state`](#dialogstatein) field with the next
     * `Assist` RPC. (The client does not need to interpret or otherwise use this
     * value.) This information should be saved across device reboots. However,
     * this value should be cleared (not saved in the client) during a
     * factory-default reset.
     * </pre>
     *
     * <code>bytes conversation_state = 2;</code>
     */
    public com.google.protobuf.ByteString getConversationState() {
      return conversationState_;
    }
    /**
     * <pre>
     * *Output-only* State information for the subsequent `Assist` RPC. This
     * value should be saved in the client and returned in the
     * [`DialogStateIn.conversation_state`](#dialogstatein) field with the next
     * `Assist` RPC. (The client does not need to interpret or otherwise use this
     * value.) This information should be saved across device reboots. However,
     * this value should be cleared (not saved in the client) during a
     * factory-default reset.
     * </pre>
     *
     * <code>bytes conversation_state = 2;</code>
     */
    public Builder setConversationState(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      conversationState_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * *Output-only* State information for the subsequent `Assist` RPC. This
     * value should be saved in the client and returned in the
     * [`DialogStateIn.conversation_state`](#dialogstatein) field with the next
     * `Assist` RPC. (The client does not need to interpret or otherwise use this
     * value.) This information should be saved across device reboots. However,
     * this value should be cleared (not saved in the client) during a
     * factory-default reset.
     * </pre>
     *
     * <code>bytes conversation_state = 2;</code>
     */
    public Builder clearConversationState() {
      
      conversationState_ = getDefaultInstance().getConversationState();
      onChanged();
      return this;
    }

    private int microphoneMode_ = 0;
    /**
     * <pre>
     * *Output-only* Specifies the mode of the microphone after this `Assist`
     * RPC is processed.
     * </pre>
     *
     * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
     */
    public int getMicrophoneModeValue() {
      return microphoneMode_;
    }
    /**
     * <pre>
     * *Output-only* Specifies the mode of the microphone after this `Assist`
     * RPC is processed.
     * </pre>
     *
     * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
     */
    public Builder setMicrophoneModeValue(int value) {
      microphoneMode_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * *Output-only* Specifies the mode of the microphone after this `Assist`
     * RPC is processed.
     * </pre>
     *
     * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
     */
    public com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode getMicrophoneMode() {
      @SuppressWarnings("deprecation")
      com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode result = com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode.valueOf(microphoneMode_);
      return result == null ? com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode.UNRECOGNIZED : result;
    }
    /**
     * <pre>
     * *Output-only* Specifies the mode of the microphone after this `Assist`
     * RPC is processed.
     * </pre>
     *
     * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
     */
    public Builder setMicrophoneMode(com.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode value) {
      if (value == null) {
        throw new NullPointerException();
      }
      
      microphoneMode_ = value.getNumber();
      onChanged();
      return this;
    }
    /**
     * <pre>
     * *Output-only* Specifies the mode of the microphone after this `Assist`
     * RPC is processed.
     * </pre>
     *
     * <code>.google.assistant.embedded.v1alpha2.DialogStateOut.MicrophoneMode microphone_mode = 3;</code>
     */
    public Builder clearMicrophoneMode() {
      
      microphoneMode_ = 0;
      onChanged();
      return this;
    }

    private int volumePercentage_ ;
    /**
     * <pre>
     * *Output-only* Updated volume level. The value will be 0 or omitted
     * (indicating no change) unless a voice command such as *Increase the volume*
     * or *Set volume level 4* was recognized, in which case the value will be
     * between 1 and 100 (corresponding to the new volume level of 1% to 100%).
     * Typically, a client should use this volume level when playing the
     * `audio_out` data, and retain this value as the current volume level and
     * supply it in the `AudioOutConfig` of the next `AssistRequest`. (Some
     * clients may also implement other ways to allow the current volume level to
     * be changed, for example, by providing a knob that the user can turn.)
     * </pre>
     *
     * <code>int32 volume_percentage = 4;</code>
     */
    public int getVolumePercentage() {
      return volumePercentage_;
    }
    /**
     * <pre>
     * *Output-only* Updated volume level. The value will be 0 or omitted
     * (indicating no change) unless a voice command such as *Increase the volume*
     * or *Set volume level 4* was recognized, in which case the value will be
     * between 1 and 100 (corresponding to the new volume level of 1% to 100%).
     * Typically, a client should use this volume level when playing the
     * `audio_out` data, and retain this value as the current volume level and
     * supply it in the `AudioOutConfig` of the next `AssistRequest`. (Some
     * clients may also implement other ways to allow the current volume level to
     * be changed, for example, by providing a knob that the user can turn.)
     * </pre>
     *
     * <code>int32 volume_percentage = 4;</code>
     */
    public Builder setVolumePercentage(int value) {
      
      volumePercentage_ = value;
      onChanged();
      return this;
    }
    /**
     * <pre>
     * *Output-only* Updated volume level. The value will be 0 or omitted
     * (indicating no change) unless a voice command such as *Increase the volume*
     * or *Set volume level 4* was recognized, in which case the value will be
     * between 1 and 100 (corresponding to the new volume level of 1% to 100%).
     * Typically, a client should use this volume level when playing the
     * `audio_out` data, and retain this value as the current volume level and
     * supply it in the `AudioOutConfig` of the next `AssistRequest`. (Some
     * clients may also implement other ways to allow the current volume level to
     * be changed, for example, by providing a knob that the user can turn.)
     * </pre>
     *
     * <code>int32 volume_percentage = 4;</code>
     */
    public Builder clearVolumePercentage() {
      
      volumePercentage_ = 0;
      onChanged();
      return this;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:google.assistant.embedded.v1alpha2.DialogStateOut)
  }

  // @@protoc_insertion_point(class_scope:google.assistant.embedded.v1alpha2.DialogStateOut)
  private static final com.google.assistant.embedded.v1alpha2.DialogStateOut DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.google.assistant.embedded.v1alpha2.DialogStateOut();
  }

  public static com.google.assistant.embedded.v1alpha2.DialogStateOut getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<DialogStateOut>
      PARSER = new com.google.protobuf.AbstractParser<DialogStateOut>() {
    @java.lang.Override
    public DialogStateOut parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new DialogStateOut(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<DialogStateOut> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<DialogStateOut> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.assistant.embedded.v1alpha2.DialogStateOut getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

