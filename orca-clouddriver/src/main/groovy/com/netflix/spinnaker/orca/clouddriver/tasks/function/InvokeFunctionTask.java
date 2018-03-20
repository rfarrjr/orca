package com.netflix.spinnaker.orca.clouddriver.tasks.function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.netflix.spinnaker.orca.ExecutionStatus;
import com.netflix.spinnaker.orca.Task;
import com.netflix.spinnaker.orca.TaskResult;
import com.netflix.spinnaker.orca.clouddriver.KatoService;
import com.netflix.spinnaker.orca.clouddriver.model.TaskId;
import com.netflix.spinnaker.orca.clouddriver.tasks.AbstractCloudProviderAwareTask;
import com.netflix.spinnaker.orca.pipeline.model.Stage;


@Component
public class InvokeFunctionTask extends AbstractCloudProviderAwareTask implements Task {
  private static final String CLOUD_OPERATION_TYPE = "invokeFunction";

  private final KatoService kato;

  @Autowired
  public InvokeFunctionTask(final KatoService kato) {
    this.kato = kato;
  }

  @Override
  public TaskResult execute(Stage stage) {
    String cloudProvider = getCloudProvider(stage);
    String account = getCredentials(stage);

    final TaskId taskId = kato.requestOperations(cloudProvider, Lists.newArrayList(ImmutableMap.of(CLOUD_OPERATION_TYPE,
                                                                                                   stage.getContext())))
                              .toBlocking()
                              .first();

    return new TaskResult(ExecutionStatus.SUCCEEDED, ImmutableMap.of("notification.type"  , "invokefunction",
                                                                     "function.account.name", account,
                                                                     "kato.last.task.id"  , taskId));
  }
}
