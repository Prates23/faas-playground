package com.openfaas.function;

import com.openfaas.model.IHandler;
import com.openfaas.model.IResponse;
import com.openfaas.model.IRequest;
import com.openfaas.model.Response;

import com.google.gson.*;

public class Handler implements com.openfaas.model.IHandler {

    public IResponse Handle(IRequest req) {
        Response res = new Response();
	    
	    Gson gson = new Gson();
	    
	    
	    FibonacciInput fibonacciInput = gson.fromJson(req.getBody(), FibonacciInput.class);
	    
	    String body = Integer.toString(fibonacci(fibonacciInput.getFibonacciInput()));
	    
	    res.setBody("Fibonacci value of "+fibonacciInput.getFibonacciInput()+" is "+body);

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

class FibonacciInput{
		private int n=1;
		
		public int getFibonacciInput(){
			return this.n;
		}
}
