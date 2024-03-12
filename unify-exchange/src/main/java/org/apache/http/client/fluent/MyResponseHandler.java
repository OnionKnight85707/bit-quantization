package org.apache.http.client.fluent;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.entity.ContentType;
import org.apache.http.util.EntityUtils;


public class MyResponseHandler implements ResponseHandler<Content> {
    public MyResponseHandler() {
    }

    @Override
    public Content handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        StatusLine statusLine = response.getStatusLine();
        HttpEntity entity = response.getEntity();
        return entity != null ? new Content(EntityUtils.toByteArray(entity), ContentType.getOrDefault(entity)) : Content.NO_CONTENT;
    }
}
