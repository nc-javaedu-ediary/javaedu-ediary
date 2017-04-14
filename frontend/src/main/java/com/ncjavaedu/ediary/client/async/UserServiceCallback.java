package com.ncjavaedu.ediary.client.async;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.ncjavaedu.ediary.model.User;

import java.util.List;

/**
 * Created by abogdanov on 13.04.17.
 */
public class UserServiceCallback implements AsyncCallback<List<User>> {
    @Override
    public void onFailure(Throwable caught) {
        Window.alert("Unable to get response from server" + caught.getMessage());
    }

    @Override
    public void onSuccess(List<User> result){
        Window.alert(result.toString());
    }
}
