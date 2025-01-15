package com.keywordr
package process.api

trait ExecutionPlan[A] {
  def initialize(initializer: Map[String, String]): Unit
  def execute(): A
}
