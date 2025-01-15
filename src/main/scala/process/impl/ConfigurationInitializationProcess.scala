package com.keywordr
package process.impl

object ConfigurationInitializationProcess extends ExecutionPlan[Boolean] {
  override def initialize(initializer: Map[String, String]): Unit {}

  override def execute(): Boolean = {
    List[String] validationMessages = ConfigurationProvider.initialize()

    validationMessages.isEmpty()
  }
}
