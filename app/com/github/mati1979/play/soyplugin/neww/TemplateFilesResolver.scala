package com.github.mati1979.play.soyplugin.neww

import java.net.URL

import scala.util.Try

/**
 * Created by mati on 09/08/2014.
 */
trait TemplateFilesResolver {

  /**
   * Iterate over all files and provide urls of files pointing to soy template files
   * @return resolved url
   */
  def resolve: Try[List[URL]]

  /**
   * Iterate over all files and provide a matching url for a template passed in as a parameter
   * @param templateName - name of template
   * @return resolved url
   */
  def resolve(templateName: String): Option[URL]

}
