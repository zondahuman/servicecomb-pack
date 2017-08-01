/*
 * Copyright 2017 Huawei Technologies Co., Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.servicecomb.saga.core;

public class SagaRequestImpl implements SagaRequest {

  private final String id;
  private final String serviceName;
  private final Transaction transaction;
  private final Compensation compensation;
  private final SagaTask sagaTask;


  public SagaRequestImpl(String id, Transaction transaction, Compensation compensation, SagaTask sagaTask) {
    this(id, "Saga", transaction, compensation, sagaTask);
  }

  public SagaRequestImpl(String id,
      String serviceName,
      Transaction transaction,
      Compensation compensation,
      SagaTask sagaTask) {

    this.id = id;
    this.serviceName = serviceName;
    this.transaction = transaction;
    this.compensation = compensation;
    this.sagaTask = sagaTask;
  }

  public SagaRequestImpl(SagaRequest request, SagaTask sagaTask) {
    this(request.id(), request.serviceName(), request.transaction(), request.compensation(), sagaTask);
  }

  @Override
  public void commit() {
    sagaTask.commit(this);
  }

  @Override
  public void compensate() {
    sagaTask.compensate(this);
  }

  @Override
  public void abort(Exception e) {
    sagaTask.abort(this, e);
  }

  @Override
  public Transaction transaction() {
    return transaction;
  }

  @Override
  public Compensation compensation() {
    return compensation;
  }

  @Override
  public String serviceName() {
    return serviceName;
  }

  @Override
  public String id() {
    return id;
  }
}