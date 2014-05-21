package sample.messagepack.bean;

import org.msgpack.annotation.Message;

@Message // Annotation
public class SampleBean {

    // public fields are serialized.
	public String cmd;
    public String name;
    public int version;

}
