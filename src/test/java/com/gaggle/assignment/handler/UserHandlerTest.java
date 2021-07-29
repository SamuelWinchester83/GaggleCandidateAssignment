package com.gaggle.assignment.handler;

import com.amazonaws.services.lambda.runtime.Context;
import com.gaggle.assignment.domain.User;
import com.gaggle.assignment.service.UserService;
import com.gaggle.assignment.service.UserServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests the UserHandler class.
 */
class UserHandlerTest {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Test
    void whenUnsupportedOperationPassedIn_thenReturnFailure() {
        UserService service = mock(UserService.class);

        Map<String, String> input = new HashMap<>();
        input.put("operation", "nothing");

        String request = gson.toJson(input);
        Context context = mock(Context.class);

        UserHandler userHandler = new UserHandler();
        userHandler.setUserService(service);
        String response = userHandler.handleRequest(request, context);

        UserHandlerResponse result = gson.fromJson(response, UserHandlerResponse.class);
        assertNull(result.users);
        assertNotNull(result.cause);
        assertEquals(result.cause, UserHandler.CAUSE_UNSUPPORTED_OPERATION);
        assertNotNull(result.message);
    }

    @Test
    void whenServiceSucceeds_thenReturnCorrectUserById() throws Exception {
        User expected = new User(1, "firstName", "lastName");

        UserService service = mock(UserService.class);
        when(service.getById(anyInt())).thenReturn(expected);

        Integer id = 1;

        UserHandlerRequest ur = new UserHandlerRequest();
        ur.operation = UserOperation.SEARCH_BY_ID;
        ur.id = id;

        String request = gson.toJson(ur);
        Context context = mock(Context.class);

        UserHandler userHandler = new UserHandler();
        userHandler.setUserService(service);
        String response = userHandler.handleRequest(request, context);

        verify(service).getById(id);

        UserHandlerResponse result = gson.fromJson(response, UserHandlerResponse.class);
        assertNotNull(result.users);
        assertNull(result.cause);
        assertNull(result.message);
        assertFalse(result.users.isEmpty());
        assertEquals(result.users.size(), 1);
        User actual = result.users.get(0);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    void whenServiceFails_thenHandleSearchUserByIdFailureGracefully() throws Exception {
        UserService service = mock(UserService.class);
        when(service.getById(anyInt())).thenThrow(new UserServiceException("message", new RuntimeException()));

        Integer id = 1;

        UserHandlerRequest ur = new UserHandlerRequest();
        ur.operation = UserOperation.SEARCH_BY_ID;
        ur.id = id;

        String request = gson.toJson(ur);
        Context context = mock(Context.class);

        UserHandler userHandler = new UserHandler();
        userHandler.setUserService(service);
        String response = userHandler.handleRequest(request, context);

        verify(service).getById(id);

        UserHandlerResponse result = gson.fromJson(response, UserHandlerResponse.class);
        assertNull(result.users);
        assertNotNull(result.cause);
        assertEquals(result.cause, UserHandler.CAUSE_OPERATION_FAILED);
        assertNotNull(result.message);
    }

    @Test
    void whenServiceSucceeds_thenReturnCorrectUsersByName() throws Exception {
        String name = "Bruce";

        User expected = new User(1, "Bruce", "Wayne");
        List<User> expectedList = new ArrayList<>();
        expectedList.add(expected);

        UserService service = mock(UserService.class);
        when(service.getByName(anyString())).thenReturn(expectedList);

        UserHandlerRequest ur = new UserHandlerRequest();
        ur.operation = UserOperation.SEARCH_BY_NAME;
        ur.name = name;

        String request = gson.toJson(ur);
        Context context = mock(Context.class);

        UserHandler userHandler = new UserHandler();
        userHandler.setUserService(service);
        String response = userHandler.handleRequest(request, context);

        verify(service).getByName(name);

        UserHandlerResponse result = gson.fromJson(response, UserHandlerResponse.class);
        assertNotNull(result.users);
        assertNull(result.cause);
        assertNull(result.message);
        assertFalse(result.users.isEmpty());
        assertEquals(result.users.size(), 1);
        User actual = result.users.get(0);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    void whenServiceFails_thenHandleSearchUserByNameFailureGracefully() throws Exception {
        String name = "Bruce";

        UserService service = mock(UserService.class);
        when(service.getByName(anyString())).thenThrow(new UserServiceException("message", new RuntimeException()));

        UserHandlerRequest ur = new UserHandlerRequest();
        ur.operation = UserOperation.SEARCH_BY_NAME;
        ur.name = name;

        String request = gson.toJson(ur);
        Context context = mock(Context.class);

        UserHandler userHandler = new UserHandler();
        userHandler.setUserService(service);
        String response = userHandler.handleRequest(request, context);

        verify(service).getByName(name);

        UserHandlerResponse result = gson.fromJson(response, UserHandlerResponse.class);
        assertNull(result.users);
        assertNotNull(result.cause);
        assertEquals(result.cause, UserHandler.CAUSE_OPERATION_FAILED);
        assertNotNull(result.message);
    }
}
