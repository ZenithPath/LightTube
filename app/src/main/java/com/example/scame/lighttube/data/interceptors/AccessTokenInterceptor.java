package com.example.scame.lighttube.data.interceptors;

import com.example.scame.lighttube.data.repository.IAccountDataManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AccessTokenInterceptor implements Interceptor {

    private String accessToken;

    private IAccountDataManager accountDataManager;

    public AccessTokenInterceptor(IAccountDataManager accountDataManager) {
        this.accountDataManager = accountDataManager;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request authorisedRequest;

        accountDataManager.getTokenFromCache()
                .subscribe(tokenEntity -> accessToken = tokenEntity.getAccessToken());

        if (!accessToken.equals("") && !isCommentThreadsRequest(originalRequest)) {
            authorisedRequest = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + accessToken)
                    .build();

        } else {
            authorisedRequest = originalRequest;
        }

        return chain.proceed(authorisedRequest);
    }

    // adding a token to this request causes 403 forbidden response code
    private boolean isCommentThreadsRequest(Request request) {
        String searchSequence = "commentThreads".toLowerCase();
        String url = request.url().toString().toLowerCase();

        String searchMethod = "GET".toLowerCase();
        String requestMethod = request.method().toLowerCase();

        return url.contains(searchSequence) && searchMethod.equals(requestMethod);
    }
}
