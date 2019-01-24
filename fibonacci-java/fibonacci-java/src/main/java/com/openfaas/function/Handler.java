package com.openfaas.function;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

public class Handler implements com.openfaas.model.IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
	    res.setBody("Hello, world!");

	    return res;
    }
    
	public int fibonacci(int n)  {
		if(n == 0){
			return 0;
		}else if(n == 1){
		  return 1;
	    }else{
		  return fibonacci(n - 1) + fibonacci(n - 2);
		}
	}
}
