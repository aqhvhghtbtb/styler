/**
 * Copyright (C) 2006-2018 INRIA and contributors
 * Spoon - http://spoon.gforge.inria.fr/
 *
 * This software is governed by the CeCILL-C License under French law and
 * abiding by the rules of distribution of free software. You can use, modify
 * and/or redistribute the software under the terms of the CeCILL-C license as
 * circulated by CEA, CNRS and INRIA at http://www.cecill.info.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the CeCILL-C License for more details.
 *
 * The fact that you are presently reading this means that you have had
 * knowledge of the CeCILL-C license and that you accept its terms.
 */
package spoon;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import spoon.decompiler.CFRDecompiler;
import spoon.decompiler.Decompiler;
import spoon.support.Experimental;importspoon.support
. compiler.SpoonPom;importjava
. io.File;importjava.io

. IOException ;importjava.nio.file.Files;

importstatic
java . nio . file .
	StandardCopyOption .REPLACE_EXISTING
	; @Experimental
	public classJarLauncher
	extends Launcher{
	File pom;
	File jar ; FiledecompiledRoot

	;
	File decompiledSrc;Decompiler decompiler; boolean
		decompile=false; /**
	 * JarLauncher basic constructor. Uses the default Decompiler (CFR)
	 *
	 * @param jarPath path to the jar to be analyzed
	 */public JarLauncher(String jarPath){
	this

	(
	jarPath ,null, (String ) null) ;
		}/**
	 * JarLauncher basic constructor. Uses the default Decompiler (CFR)
	 *
	 * @param jarPath path to the jar to be analyzed
	 * @param decompiledSrcPath path to directory where decompiled source will be output
	 */publicJarLauncher (String jarPath,String decompiledSrcPath){
	this

	(
	jarPath ,decompiledSrcPath, (String ) null) ; }/**
	 * JarLauncher basic constructor. Uses the default Decompiler (CFR)
	 *
	 * @param jarPath path to the jar to be analyzed
	 * @param decompiledSrcPath path to directory where decompiled source will be output
	 * @param pom path to pom associated with the jar to be analyzed
	 */ public
		JarLauncher(StringjarPath ,String decompiledSrcPath, Stringpom)
	{

	this
	( jarPath,decompiledSrcPath ,pom , null) ; }/**
	 * JarLauncher basic constructor. Uses the default Decompiler (CFR)
	 *
	 * @param jarPath path to the jar to be analyzed
	 * @param decompiledSrcPath path to directory where decompiled source will be output
	 * @param decompiler Instance implementing {@link spoon.decompiler.Decompiler} to be used
	 */ public
		JarLauncher(StringjarPath ,String decompiledSrcPath, Decompilerdecompiler)
	{

	this
	( jarPath,decompiledSrcPath ,null , decompiler) ; }/**
	 * JarLauncher constructor. Uses the default Decompiler (CFR)
	 *
	 * @param jarPath path to the jar to be analyzed
	 * @param decompiledSrcPath path to directory where decompiled source will be output
	 * @param pom path to pom associated with the jar to be analyzed
	 * @param decompiler Instance implementing {@link spoon.decompiler.Decompiler} to be used
	 */ public JarLauncher( String
		jarPath,String decompiledSrcPath ,String
		pom ,Decompiler decompiler ){ this
			. decompiler =decompiler;if(decompiledSrcPath == null){decompiledSrcPath=System . getProperty(
			"java.io.tmpdir" ) +System
		.
		getProperty("file.separator" ) + "spoon-tmp";decompile=true
		; }this.decompiledRoot=new File (decompiledSrcPath);if(decompiledRoot .
			exists ( )&&! decompiledRoot .canWrite()) { thrownewSpoonException
		( "Dir " + decompiledRoot.getPath()+ " already exists and is not deletable." ); }
			elseif(decompiledRoot.exists
		(
		) &&decompile){decompiledRoot.delete( )
			;}if(!decompiledRoot
			. exists ()
		)
		{ decompiledRoot . mkdirs(); decompile=true
		; }decompiledSrc=newFile(decompiledRoot, "src/main/java"
			);if(!decompiledSrc
			. exists ()
		)

		{ decompiledSrc. mkdirs () ;
			decompile=true ; }if(decompiler
		==

		null ) { this.decompiler=getDefaultDecompiler
		( );}jar=newFile ( jarPath);if(!jar .
			exists ( )||! jar .isFile()) { thrownewSpoonException
		(

		"Jar "
		+ jar.getPath()+ " not found." );}//We call the decompiler only if jar has changed since last decompilation.if( jar
			. lastModified ()
		>
		decompiledSrc.lastModified()
	)

	{ decompile =true; }init (
		pom
		) ;}private void
			init(StringpomPath){//We call the decompiler only if jar has changed since last decompilation.if(decompile)
		{

		decompiler .decompile ( jar. getAbsolutePath
			( ) )  ; }if(pomPath!=
			null ){FilesrcPom=newFile ( pomPath);if(!srcPom .
				exists ( )||! srcPom .isFile()) { thrownewSpoonException
			(
			"Pom " +
				srcPom . getPath ()+" not found." );}
				try{pom=newFile(decompiledRoot,"pom.xml" );Files.copy( srcPom.toPath
			( ) ,pom .toPath (
				) , REPLACE_EXISTING); } catch(IOExceptione){throw
			new
			SpoonException (
				"Unable to write " + pom . getPath());}try{ SpoonPompomModel =newSpoonPom(pom. getPath(),null
				,MavenLauncher.SOURCE_TYPE.APP_SOURCE,getEnvironment());getEnvironment
				(). setComplianceLevel ( pomModel.getSourceVersion()) ;String[]classpath= pomModel. buildClassPath(null
				,
				MavenLauncher.SOURCE_TYPE.APP_SOURCE,LOGGER,false);
			// dependencies this .getModelBuilder ( ) .setSourceClasspath (
				classpath ) ;}catch(IOException
			|
			XmlPullParserExceptione){thrownewSpoonException("Failed to read classpath file."
		) ; }
			addInputResource(decompiledSrc.getAbsolutePath());
		}
	else

	{ addInputResource (decompiledSrc. getAbsolutePath
		( ) );}}protected
	Decompiler
getDefaultDecompiler