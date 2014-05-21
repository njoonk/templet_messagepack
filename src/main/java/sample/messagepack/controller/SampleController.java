package sample.messagepack.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Writer;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import sample.messagepack.bean.message.MyMessage;

import org.apache.commons.configuration.Configuration;
import org.msgpack.MessagePack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * The <code>TestTblController</code> class represents action controller.
 * 1. Explain for a method 
 * 
 * @author  Kim Joon
 * @version 0.1, 12/05/15
 * @see     sample.messagepack.controller.SampleController#index()
 * @since   JDK1.7
 */
@Controller
@RequestMapping("/test")
public class SampleController {

	final Logger logger = LoggerFactory.getLogger(SampleController.class);

	@Autowired
	private MessageSource message;
	@Autowired
    private Configuration configuration;

    /**
     * Index for ......
     * 
     * @param  ModelMap 
     *         model
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @return String
     * 		   a file name of FTL.
     * 
     * @since  1.7
     */
    @RequestMapping(value = {"/", "", "index.sp"}, method = RequestMethod.POST)
    public void index(@RequestBody String body, BindingResult bindingResult, Writer writer, HttpServletResponse response) throws IOException {

    	String model = null;

    	MessagePack msgpack = new MessagePack();
    	MyMessage readMessage = msgpack.read(body.getBytes(), MyMessage.class);

		// Validate the parameters.
		SampleValidator validator = new SampleValidator();
		validator.validate(readMessage, bindingResult);
		// If it occurs a error, set the default value.
		if (bindingResult.hasErrors()) {
			logger.error("behavior.sp >>>> It is occured a parameter error.");
			model = message.getMessage("sample.error.400.title", null, Locale.JAPAN);
			writer.write(model);
			return;
		}

        MyMessage writeMessage = new MyMessage();
        writeMessage.name = "msgpack";
        writeMessage.version = 1;

        // Serialize
        byte[] bytesMessage = msgpack.write(writeMessage);
        model  = new String(bytesMessage,0,bytesMessage.length);
        writer.write(model);
    }

    /**
     * Second for ......
     * 
     * @param  ModelMap 
     *         model
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @return String
     * 		   a file name of FTL.
     * 
     * @since  1.7
     */
    @RequestMapping(value = {"second.sp"}, method = RequestMethod.POST)
    public void second(@RequestBody String body, BindingResult bindingResult, HttpServletResponse response) throws IOException {

    	// Read
    	MessagePack msgpack = new MessagePack();
    	MyMessage readMessage = msgpack.read(body.getBytes(), MyMessage.class);

    	// Write    	
        MyMessage writeMessage = new MyMessage();
        writeMessage.name = "msgpack";
        writeMessage.version = 1;
        // Serialize
        byte[] bytesMessage = msgpack.write(writeMessage);
        response.setContentType("text/html;charset=UTF-8");
        response.setBufferSize(2048);
        response.setContentLength(bytesMessage.length);

        OutputStream os = response.getOutputStream();
        InputStream in = new ByteArrayInputStream(bytesMessage);
        int n = -1;
        while((n = in.read(bytesMessage)) > 0) {
        	os.write(bytesMessage, 0, n);
        }
        in.close();
        os.flush();
        os.close();

    }

    /**
     * Third for ......
     * 
     * @param  ModelMap 
     *         model
     *         
     * @throws  Exception
     *          If a error occur, ...
     *
     * @return String
     * 		   a file name of FTL.
     * 
     * @since  1.7
     */
    @RequestMapping(value = {"third.sp"}, method = RequestMethod.POST)
    public void third(@RequestBody String body, BindingResult bindingResult, HttpServletResponse response) throws IOException {

    	// Read
    	MessagePack msgpack = new MessagePack();
    	MyMessage readMessage = msgpack.read(body.getBytes(), MyMessage.class);

    	// Write
        MyMessage writeMessage = new MyMessage();
        writeMessage.name = "msgpack";
        writeMessage.version = 1;
        // Serialize
        byte[] bytesMessage = msgpack.write(writeMessage);

        response.setContentLength(bytesMessage.length);
        ServletOutputStream outPut = response.getOutputStream();
        outPut.write(bytesMessage);
        outPut.close();
    }

}