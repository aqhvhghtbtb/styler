package fr.inria.spirals.repairnator;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * This class can be used in gson to properly serialize Path object.
 */
public class GsonPathTypeAdapter extends TypeAdapter<Path> {
@
Override public voidwrite( JsonWriterjsonWriter , Pathpath ) throws IOException
{jsonWriter.beginObject()
;jsonWriter.name("path").value(path.toAbsolutePath().toString())
;jsonWriter.endObject()
;

}@
Override public Pathread( JsonReaderjsonReader ) throws IOException
{jsonReader.beginObject()
; String absolutePath =jsonReader.nextString()
;jsonReader.endObject()
; return newFile(absolutePath).toPath()
;
}