package br.com.quixada.ufc.steambackend.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import br.com.quixada.ufc.steambackend.model.Profile;

public class ProfileController {
  public boolean insertProfile(String name, String email, String nickname, String location) throws IOException{
    Profile profile = new Profile(name, email, nickname, location);

    CsvMapper mapper = new CsvMapper();
		CsvSchema schema = mapper.schemaFor(Profile.class);
    ObjectWriter writer = mapper.writer(schema);
    OutputStream outstream = new FileOutputStream("steambackend\\src\\main\\java\\br\\com\\quixada\\ufc\\steambackend\\output\\data.csv", true);
    writer.writeValue(outstream, profile);

    return true;
  }
  
  public int sizeData() throws IOException{
    InputStream is  = new FileInputStream("steambackend/src/main/java/br/com/quixada/ufc/steambackend/output/data.csv");
    InputStreamReader isr = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(isr);
    String s = br.readLine();
    Integer size = 0;
    s = br.readLine();

    while (s != null) {
      s = br.readLine();

      if(s != ""){
        size++;
      }
    }

    br.close();
    return size;
  }

  public boolean convertToJSON(){
    File input = new File("input.csv");
    try {
      CsvSchema csv = CsvSchema.emptySchema().withHeader();
      CsvMapper csvMapper = new CsvMapper();
      MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv).readValues(input);
      List<Map<?, ?>> list = mappingIterator.readAll();
      System.out.println(list);
      return true;
    } catch(Exception e) {
      return false;
    }
  }

  public boolean convertToXML(){
    return false;
  }

  public boolean compressData(){
    return false;
  }

  public String showHash(){
    return "";
  }
}