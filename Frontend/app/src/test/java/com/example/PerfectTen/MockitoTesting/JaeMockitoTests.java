package com.example.PerfectTen.MockitoTesting;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import android.graphics.Bitmap;

import com.example.PerfectTen.Screens.SettingsScreen;
import com.example.PerfectTen.net_utils.Const;
import com.example.PerfectTen.net_utils.PerfectTenRequester;

import org.json.JSONObject;
import org.junit.Test;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

public class JaeMockitoTests {

    @Test
    public void testChangePfp() {

        Map<String, String> base = new HashMap<>();
        base.put("title", "I love bowling!");
        base.put("message", "It's so much fun!");

        JSONObject temp = new JSONObject(base);

        ServerMock mServer = mock(ServerMock.class);

        when(mServer.getPost(14)).thenReturn(temp);

        JSONObject result = mServer.getPost(14);

        assertEquals(temp, result);
    }

    @Test
    public void testGetJsonObj() {

        SettingsScreen mSettings = mock(SettingsScreen.class);

        Map<String, String> setup = new HashMap<>();
        setup.put("username", "Jae");
        setup.put("password", "JaeIsAwesome");
        setup.put("email", "jaeswan@iastate.edu");

        JSONObject expected = new JSONObject(setup);

        when(mSettings.getJsonObject("Jae", "JaeIsAwesome", "jaeswan@iastate.edu")).thenReturn(new JSONObject(setup));

        JSONObject result = mSettings.getJsonObject("Jae", "JaeIsAwesome", "jaeswan@iastate.edu");

        assertEquals(expected, result);
    }

}