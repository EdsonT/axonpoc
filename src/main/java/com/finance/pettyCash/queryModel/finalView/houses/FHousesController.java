package com.finance.pettyCash.queryModel.finalView.houses;

import com.finance.pettyCash.commons.dtos.response.Response;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.responsetypes.MultipleInstancesResponseType;
import org.axonframework.queryhandling.responsetypes.ResponseTypes;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
@RequestMapping("houses")
class FHousesController {
    @Autowired
    QueryGateway gateway;

//    @GetMapping("")
//    public Response listFHouses(FHousesQuery params) throws ExecutionException,
//            InterruptedException {
//        Response response = new Response();
//                        Future<List<FHouses>> future = gateway.query(new JSONObject(params), new MultipleInstancesResponseType<>(FHouses.class));
//                        response.setContent(future.get());
//                        return response;
//    }
//
//    @GetMapping("/{code}")
//    public Response getFHouses(@PathVariable String code) throws ExecutionException,
//            InterruptedException {
//        Response response = new Response();
//                        Future<FHouses> future = gateway.query(code, ResponseTypes.instanceOf(FHouses.class));
//                        response.setContent(future.get());
//                        return response;
//    }
//
//    @GetMapping("/findOne")
//    public Response findOneFHouses(FHousesQuery params) throws ExecutionException,
//            InterruptedException {
//        Response response = new Response();
//                        Future<FHouses> future = gateway.query(new JSONObject(params), ResponseTypes.instanceOf(FHouses.class));
//                        response.setContent(future.get());
//                        return response;
//    }
}
