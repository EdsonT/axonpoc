package com.finance.pettyCash.controllers;

import com.finance.pettyCash.base.BaseController.BaseController;
import com.finance.pettyCash.commandModel.commands.CloseCommand;
import com.finance.pettyCash.commandModel.commands.CorrectCommand;
import com.finance.pettyCash.commandModel.commands.EntryCommand;
import com.finance.pettyCash.commandModel.commands.OpenCommand;
import com.finance.pettyCash.commons.dtos.request.PettyCashRequest;
import com.finance.pettyCash.commons.dtos.response.Response;
import com.finance.pettyCash.commons.helpers.ValidatorHelper;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.common.IdentifierFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "petty-cash")
public class DailyBookController extends BaseController {
    protected final IdentifierFactory identifierFactory = IdentifierFactory.getInstance();

    @Autowired
    CommandGateway commandGateway;

    @RequestMapping(value = "/open", method = RequestMethod.POST)
    public ResponseEntity openPettyCash(@RequestBody PettyCashRequest request) throws ExecutionException, InterruptedException, IOException {
        ValidatorHelper validator = validateRequest(request, "/schemas/openRequest.json");
        if (validator.isValid()) {
            CompletableFuture command = commandGateway.send(new OpenCommand(request, identifierFactory.generateIdentifier()));
            return ResponseEntity.ok((new Response()).getCommandReturn(command.get()));
        } else {
            return getErrors(validator.getError());
        }
    }

    @RequestMapping(value = "/entry", method = RequestMethod.POST)
    public ResponseEntity entryPettyCash(@RequestBody PettyCashRequest request) throws ExecutionException, InterruptedException, IOException {
        ValidatorHelper validator = validateRequest(request, "/schemas/entryCloseRequest.json");
        if (validator.isValid()) {
            CompletableFuture command = commandGateway.send(new EntryCommand(request.getAggregateCode(), request));
            command.get();
            return ResponseEntity.ok((new Response()).getCommandReturn(request.getAggregateCode()));
        } else {
            return getErrors(validator.getError());
        }
    }

    @RequestMapping(value = "/correct", method = RequestMethod.POST)
    public ResponseEntity correctEntryPettyCash(@RequestBody PettyCashRequest request) throws IOException, ExecutionException, InterruptedException {
        ValidatorHelper validator = validateRequest(request, "/schemas/entryCloseRequest.json");
        if (validator.isValid()) {
            CompletableFuture command = commandGateway.send(new CorrectCommand(request, request.getAggregateCode()));
            command.get();
            return ResponseEntity.ok((new Response()).getCommandReturn(request.getAggregateCode()));
        } else {
            return getErrors(validator.getError());
        }
    }

    @RequestMapping(value = "/close", method = RequestMethod.POST)
    public ResponseEntity closePettyCash(@RequestBody PettyCashRequest request) throws ExecutionException, InterruptedException, IOException {
        ValidatorHelper validator = validateRequest(request, "/schemas/entryCloseRequest.json");
        if (validator.isValid()) {
            CompletableFuture command = commandGateway.send(new CloseCommand(request, request.getAggregateCode()));
            command.get();
            return ResponseEntity.ok((new Response()).getCommandReturn(request.getAggregateCode()));
        } else {
            return getErrors(validator.getError());
        }
    }
}
