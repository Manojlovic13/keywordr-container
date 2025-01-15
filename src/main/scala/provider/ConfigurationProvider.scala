package com.keywordr
package provider

import java.util.Properties
import java.io.FileInputStream
import org.apache.commons.lang3.math.NumberUtils

object ConfigurationProvider {
  private val configurationFilePath: Option[String] = if (System.getProperty("os.name").toLowerCase.contains("mac")) Some("../configuration/keywordr_configuration.properties") else None
  private val configurationFile: Properties = new Properties()
  private val BOOLEAN_VALID_VALUES = Array("true", "false", "1", "0")
  private val OUTPUT_MODE_VALID_VALUES = Array("delete", "append")
  private val LOG_LEVEL_VALID_VALUES = Array("debug", "info", "off")
  val PROPERTY_COMPANY_JSON_FILE = "company_json_file_path"
  val PROPERTY_TECH_KEYWORDS_CSV_FILE = "tech_keywords_file_path"
  val PROPERTY_JOB_KEYWORDS_CSV_FILE = "job_keywords_file_path"
  val PROPERTY_USE_SELENIUM = "use_selenium"
  val PROPERTY_JS_EXECUTION_TIMEOUT = "js_execution_timeout"
  val PROPERTY_OUTPUT_JSON_FILE = "output_json_file_path"
  val PROPERTY_OUTPUT_MODE = "output_mode"
  val PROPERTY_DEFAULT_LOG_LEVEL = "default_log_level"
  val PROPERTY_FIRESTORE_ACCOUNT_KEY = "firestore_service_account_key_path"
  val PROPERTY_FIRESTORE_UPLOAD = "firestore_upload"
  val PROPERTY_DATABASE_URL = "firestore_database_url"
  val PROPERTY_COLLECTION_NAME = "firestore_collection_name"
  val PROPERTY_DOCUMENT_ID = "firestore_document_id"

  configurationFile.load(new FileInputStream(configurationFilePath.getOrElse(throw new RuntimeException("Configuration file not found on location '../configuration/keywordr-configuration.properties"))))
  private val configurationMap: Map[String, String] = readConfiguration()

  def getConfigurationProperty(key: String): String = configurationMap(key)

  def initialize(): List[Option[String]] = {
    val messageList: List[Option[String]] = List()

    configurationMap.foreach {
        case (PROPERTY_COMPANY_JSON_FILE, value) => messageList ++ validatePath(value, PROPERTY_COMPANY_JSON_FILE)
        case (PROPERTY_TECH_KEYWORDS_CSV_FILE, value) => messageList ++ validatePath(value, PROPERTY_TECH_KEYWORDS_CSV_FILE)
        case (PROPERTY_JOB_KEYWORDS_CSV_FILE, value) => messageList ++ validatePath(value, PROPERTY_JOB_KEYWORDS_CSV_FILE)
        case (PROPERTY_USE_SELENIUM, value) => messageList ++ validateBoolean(value, PROPERTY_USE_SELENIUM)
        case (PROPERTY_JS_EXECUTION_TIMEOUT, value) => messageList ++ validateNumber(value, PROPERTY_JS_EXECUTION_TIMEOUT)
        case (PROPERTY_OUTPUT_JSON_FILE, value) => messageList ++ validatePath(value, PROPERTY_OUTPUT_JSON_FILE)
        case (PROPERTY_OUTPUT_MODE, value) => messageList ++ validateOutputMode(value)
        case (PROPERTY_DEFAULT_LOG_LEVEL, value) => messageList ++ validateLogLevel(value)
        case (PROPERTY_FIRESTORE_ACCOUNT_KEY, value) => messageList ++ validateEmpty(value, PROPERTY_FIRESTORE_ACCOUNT_KEY)
        case (PROPERTY_FIRESTORE_UPLOAD, value) => messageList ++ validateBoolean(value, PROPERTY_FIRESTORE_UPLOAD)
        case (PROPERTY_DATABASE_URL, value) => messageList ++ validateEmpty(value, PROPERTY_DATABASE_URL)
        case (PROPERTY_COLLECTION_NAME, value) => messageList ++ validateEmpty(value, PROPERTY_COLLECTION_NAME)
        case (PROPERTY_DOCUMENT_ID, value) => messageList ++ validateEmpty(value, PROPERTY_DOCUMENT_ID)
    }

    messageList
  }

  private def readConfiguration(): Map[String, String] = {
    val configMap: Map[String, String] = Map()

    configMap += (PROPERTY_COMPANY_JSON_FILE, configurationFile.getProperty(PROPERTY_COMPANY_JSON_FILE))
    configMap += (PROPERTY_TECH_KEYWORDS_CSV_FILE, configurationFile.getProperty(PROPERTY_TECH_KEYWORDS_CSV_FILE))
    configMap += (PROPERTY_JOB_KEYWORDS_CSV_FILE, configurationFile.getProperty(PROPERTY_JOB_KEYWORDS_CSV_FILE))
    configMap += (PROPERTY_USE_SELENIUM, configurationFile.getProperty(PROPERTY_USE_SELENIUM))
    configMap += (PROPERTY_JS_EXECUTION_TIMEOUT, configurationFile.getProperty(PROPERTY_JS_EXECUTION_TIMEOUT))
    configMap += (PROPERTY_OUTPUT_JSON_FILE, configurationFile.getProperty(PROPERTY_OUTPUT_JSON_FILE))
    configMap += (PROPERTY_OUTPUT_MODE, configurationFile.getProperty(PROPERTY_OUTPUT_MODE))
    configMap += (PROPERTY_DEFAULT_LOG_LEVEL, configurationFile.getProperty(PROPERTY_DEFAULT_LOG_LEVEL))
    configMap += (PROPERTY_FIRESTORE_ACCOUNT_KEY, configurationFile.getProperty(PROPERTY_FIRESTORE_ACCOUNT_KEY))
    configMap += (PROPERTY_FIRESTORE_UPLOAD, configurationFile.getProperty(PROPERTY_FIRESTORE_UPLOAD))
    configMap += (PROPERTY_DATABASE_URL, configurationFile.getProperty(PROPERTY_DATABASE_URL))
    configMap += (PROPERTY_COLLECTION_NAME, configurationFile.getProperty(PROPERTY_COLLECTION_NAME))
    configMap += (PROPERTY_DOCUMENT_ID, configurationFile.getProperty(PROPERTY_DOCUMENT_ID))

    configMap
  }

  private def validatePath(value: String, key: String): Option[String] = {
    if (!value.contains("\\") && value.contains("/")) {
      Some(s"Non valid configuration value provided for '$key'.")
    } else {
      None
    }
  }

  private def validateBoolean(value: String, key: String): Option[String] = {
    if (!BOOLEAN_VALID_VALUES.contains(value.toLowerCase)) {
      Some(s"Non valid configuration value provided for '$key'.")
    } else {
      None
    }
  }

  private def validateNumber(value: String, key: String): Option[String] = {
    if (!NumberUtils.isCreatable(value)) {
      Some(s"Non valid configuration value provided for '$key'.")
    } else {
      None
    }
  }

  private def validateOutputMode(value: String): Option[String] = {
    if (!OUTPUT_MODE_VALID_VALUES.contains(value.toLowerCase)) {
      Some(s"Non valid configuration value provided for 'output_mode'.")
    } else {
      None
    }
  }

  private def validateEmpty(value: String, key: String): Option[String] = {
    if (value.isEmpty) {
      Some(s"Non valid configuration value provided for '$key'.")
    } else {
      None
    }
  }

  private def validateLogLevel(value: String): Option[String] = {
    if (!LOG_LEVEL_VALID_VALUES.contains(value.toLowerCase)) {
      Some(s"Non valid configuration value provided for 'default_log_level'.")
    } else {
      None
    }
  }
}
