package com.keywordr

import process.impl.ConfigurationInitializationProcess

object Main {
  def main(args: Array[String]): Unit = {
    // initialize and validate configuration
    ConfigurationInitializationProcess.execute()
  }
}