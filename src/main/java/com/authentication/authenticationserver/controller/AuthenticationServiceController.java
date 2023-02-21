package com.authentication.authenticationserver.controller;

import com.authentication.AuthenticationRequest;
import com.authentication.AuthenticationResponse;
import com.authentication.AuthenticationServiceGrpc;
import com.authentication.authenticationserver.services.AuthenticationService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;

/**
 * The type Authentication service controller.
 */
@GRpcService
public class AuthenticationServiceController extends
    AuthenticationServiceGrpc.AuthenticationServiceImplBase {
  /**
   * The Authentication service.
   */
  @Autowired
  private AuthenticationService authenticationService;

  @Override
  public void authenticateUser(final AuthenticationRequest request,
                               final StreamObserver<AuthenticationResponse> responseObserver) {
    try {
      responseObserver.onNext(authenticationService.authenticateUser(request));
      responseObserver.onCompleted();
    } catch (BadCredentialsException e) {
      responseObserver.onError(Status.UNAUTHENTICATED.asRuntimeException());
    } catch (Exception e) {
      responseObserver.onError(Status.INTERNAL.asRuntimeException());
    }
  }
}
