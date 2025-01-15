package com.keywordr
package process.impl

import process.api.ExecutionPlan

import com.keywordr.provider.ConfigurationProvider

object ConfigurationInitializationProcess extends ExecutionPlan[Boolean] {

  override def execute(): Boolean = {
    val validationMessages: List[String] = ConfigurationProvider.initialize().flatten

    if (validationMessages.nonEmpty) {
      validationMessages.foreach {m => println(m)}
      return false
    }

    true
  }

  override def initialize(initializer: Map[String, String]): Unit = ???
}
