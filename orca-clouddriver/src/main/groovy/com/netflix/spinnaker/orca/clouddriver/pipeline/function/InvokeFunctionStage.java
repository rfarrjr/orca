package com.netflix.spinnaker.orca.clouddriver.pipeline.function;

import javax.annotation.Nonnull;

import org.springframework.stereotype.Component;

import com.netflix.spinnaker.orca.clouddriver.tasks.MonitorKatoTask;
import com.netflix.spinnaker.orca.clouddriver.tasks.function.InvokeFunctionTask;
import com.netflix.spinnaker.orca.pipeline.StageDefinitionBuilder;
import com.netflix.spinnaker.orca.pipeline.TaskNode;
import com.netflix.spinnaker.orca.pipeline.model.Execution;
import com.netflix.spinnaker.orca.pipeline.model.Stage;

@Component
public class InvokeFunctionStage implements StageDefinitionBuilder {

  @Override
  public void taskGraph(@Nonnull final Stage stage, @Nonnull final TaskNode.Builder builder) {
    builder.withTask("invokeFunction", InvokeFunctionTask.class)
           .withTask("monitorTask", MonitorKatoTask.class);
  }
}
