package com.github.mati1979.play.soyplugin.neww

import java.io.File
import java.net.URL

import com.github.mati1979.play.soyplugin.config.SoyViewConf
import play.Play

import scala.collection.mutable.ListBuffer
import scala.util.{Failure, Success, Try}

/**
 * Created by mati on 11/08/2014.
 */
class TemplateFileResolverImpl(soyViewConf: SoyViewConf) extends TemplateFilesResolver {

  /**
   * Iterate over all files and provide urls of files pointing to soy template files
   * @return resolved url
   */
  override def resolve = ???

  /**
   * Iterate over all files and provide a matching url for a template passed in as a parameter
   * @param templateName - name of template
   * @return resolved url
   */
  override def resolve(templateName: String) = ???

  private def toFiles(templatesLocation: String): Try[List[URL]] = {
    val templateFiles: ListBuffer[URL] = ListBuffer()
    val baseDirectory: File = Play.application.getFile(templatesLocation)

    if (baseDirectory.isDirectory) {
      Failure(new IllegalArgumentException("Soy template base directory:" + templatesLocation + "' is not a directory"))
    } else {
      templateFiles ++= findSoyFiles(baseDirectory, soyViewConf.resolveRecursive)
      Success(templateFiles.toList)
    }
  }

  protected def findSoyFiles(baseDirectory: File, recursive: Boolean): List[URL] = {
    val soyFiles: ListBuffer[URL] = ListBuffer[URL]()

    findSoyFiles(soyFiles, baseDirectory, recursive)

    return soyFiles.toList
  }

  protected def findSoyFiles(soyFiles: List[URL], baseDirectory: File, recursive: Boolean) {
    val files: Array[File] = baseDirectory.listFiles
    if (files != null) {
      for (file <- files) {
        if (file.isFile) {
          if (file.getName.endsWith(dotWithExtension)) {
            soyFiles ++= file.toURI.toURL
          }
        }
        else if (file.isDirectory && recursive) {
          findSoyFiles(soyFiles, file, recursive)
        }
      }
    }
    else {
      throw new IllegalArgumentException("Unable to retrieve contents of:" + baseDirectory)
    }
  }

  private def dotWithExtension: String = "." concat soyViewConf.resolveFilesExtension

}
