package com.khubla.jvmbasic.jvmbasicwww;

/*
 * jvmBasic Copyright 2012, khubla.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * @author tome
 */
public class JVMBASICWWW {
   /**
    * dir option
    */
   private static final String SOURCEDIR_OPTION = "sourcedir";
   /**
    * classdir option
    */
   private static final String CLASSDIR_OPTION = "classdir";
   /**
    * port option
    */
   private static final String PORT_OPTION = "port";

   @SuppressWarnings("static-access")
   public static void main(String[] args) {
      try {
         System.out.println("khubla.com jvmBasic www server");
         /*
          * options
          */
         final Options options = new Options();
         final Option sourcediroption = OptionBuilder.withArgName(SOURCEDIR_OPTION).isRequired().hasArg().withDescription("source dir").create(SOURCEDIR_OPTION);
         options.addOption(sourcediroption);
         final Option clso = OptionBuilder.withArgName(CLASSDIR_OPTION).isRequired().hasArg().withDescription("class dir").create(CLASSDIR_OPTION);
         options.addOption(clso);
         final Option po = OptionBuilder.isRequired(false).withArgName(PORT_OPTION).hasArg().withDescription("TCP port").create(PORT_OPTION);
         options.addOption(po);
         /*
          * parse
          */
         final CommandLineParser parser = new PosixParser();
         CommandLine cmd = null;
         try {
            cmd = parser.parse(options, args);
         } catch (final Exception e) {
            final HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("posix", options);
            System.exit(0);
         }
         /*
          * input dir
          */
         final String sourceDir = cmd.getOptionValue(SOURCEDIR_OPTION);
         /*
          * class dir
          */
         final String classdir = cmd.getOptionValue(CLASSDIR_OPTION);
         /*
          * port
          */
         int port = 80;
         if (cmd.hasOption(PORT_OPTION)) {
            port = Integer.parseInt(cmd.getOptionValue(PORT_OPTION));
         }
         /*
          * output the config
          */
         System.out.println("Source directory: " + sourceDir);
         System.out.println("Class directory: " + classdir);
         System.out.println("HTTP port: " + port);
         /*
          * configuration
          */
         final ServerConfiguration serverConfiguration = new ServerConfiguration(sourceDir, classdir, port);
         /*
          * server
          */
         final JVMBasicWebServer jvmBasicWebServer = new JVMBasicWebServer(serverConfiguration);
         jvmBasicWebServer.listen();
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}