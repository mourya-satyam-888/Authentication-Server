package com.authentication.authenticationserver.controller;

import com.authentication.CreateUserRequest;
import com.authentication.CreateUserResponse;
import com.authentication.CreateUserServiceGrpc;
import com.authentication.authenticationserver.exceptions.ResourceException;
import com.authentication.authenticationserver.services.CreateUserService;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * The type Create user service controller.
 */
@GRpcService
public class CreateUserServiceController extends CreateUserServiceGrpc.CreateUserServiceImplBase {
  /**
   * The Create user service.
   */
  @Autowired
  private CreateUserService createUserService;

  @Override
  public void createUser(final CreateUserRequest request,
                         final StreamObserver<CreateUserResponse> responseObserver) {
    try {
      responseObserver.onNext(createUserService.createUser(request));
      responseObserver.onCompleted();
    } catch (ResourceException e) {
      responseObserver.onError(Status.ALREADY_EXISTS.asRuntimeException());
    }
  }
}
