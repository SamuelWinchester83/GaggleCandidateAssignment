package com.gaggle.assignment.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.gaggle.assignment.domain.User;
import com.gaggle.assignment.service.UserService;
import com.gaggle.assignment.service.UserServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * Lambda function handler used to interact with users.
 */
public class UserHandler implements RequestHandler<String, String> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    protected static final String CAUSE_UNSUPPORTED_OPERATION = "Unsupported Operation";
    protected static final String CAUSE_OPERATION_FAILED = "Operation Failed";

    private UserService service;

    /**
     * Allow the UserService to be injected in.
     * @param service the business logic object used to interact with the data source
     */
    @Autowired
    public void setUserService(final UserService service) {
        this.service = service;
    }

    /**
     * Handles the request being executed in the lambda function.
     * @param event the input provided to the function execution
     * @param context contextual information of the function itself
     * @return the result of the specified execution
     */
    @Override
    public String handleRequest(final String event, final Context context) {
        UserHandlerRequest request = gson.fromJson(event, UserHandlerRequest.class);
        UserOperation operation = request.operation;

        if (operation != null) {
            switch (operation) {
                case SEARCH_BY_ID:
                    return searchById(request);
                case SEARCH_BY_NAME:
                    return searchByName(request);
                default:
                    break;
            }
        }

        List<String> supportedOperations = new ArrayList<>();
        for (final UserOperation op : UserOperation.values()) {
            supportedOperations.add(op.name().toLowerCase());
        }

        UserHandlerResponse response = new UserHandlerResponse();
        response.cause = CAUSE_UNSUPPORTED_OPERATION;
        response.message = String.format("The operation provided is not supported. " +
                "The supported operations are [%s].", String.join(",", supportedOperations));

        return gson.toJson(response);
    }

    private String searchById(final UserHandlerRequest request) {
        String result;

        try {
            List<User> users = new ArrayList<>();
            User user = service.getById(request.id);
            users.add(user);

            UserHandlerResponse response = new UserHandlerResponse();
            response.users = users;

            result = gson.toJson(response);
        } catch (final UserServiceException use) {
            UserHandlerResponse response = new UserHandlerResponse();
            response.cause = CAUSE_OPERATION_FAILED;
            response.message = "An internal issue occurred searching for a user by id.";

            result = gson.toJson(response);
        }

        return result;
    }

    private String searchByName(final UserHandlerRequest request) {
        String result;

        try {
            List<User> users = service.getByName(request.name);

            UserHandlerResponse response = new UserHandlerResponse();
            response.users = users;

            result = gson.toJson(response);
        } catch (final UserServiceException use) {
            UserHandlerResponse response = new UserHandlerResponse();
            response.cause = "Operation Failed";
            response.message = "An internal issue occurred searching for users by name.";

            result = gson.toJson(response);
        }

        return result;
    }
}
