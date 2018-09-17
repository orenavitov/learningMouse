package demo.service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.jsonrpc4j.JsonRpcServer;
import demo.enitity.HelloWorldService;
import demo.enitity.HelloWorldServiceImpl;

import java.io.IOException;

public class JsonRpcService extends HttpServlet {

    private JsonRpcServer rpcServer = null;

    public JsonRpcService() {
        super();
        rpcServer = new JsonRpcServer(new HelloWorldServiceImpl(), HelloWorldService.class);
    }

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        System.out.println("JsonRpcService service being call");
        rpcServer.handle(request, response);
    }
}
