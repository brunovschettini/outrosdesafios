package br.com.xptosystems.beans;

import br.com.xptosystems.address.Cities;
import br.com.xptosystems.security.ws.ImportCitiesRequest;
//import br.com.xptosystems.security.ws.ImportCitiesWS;
import br.com.xptosystems.utils.FileUpload;
import br.com.xptosystems.utils.Messages;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

@ManagedBean
@ViewScoped
public class ImportCitiesMB implements Serializable {

    private Part fileUpload;

    private List<Cities> listCsvCities;

    public ImportCitiesMB() {
        listCsvCities = new ArrayList();
    }

    public synchronized void process() throws NoSuchAlgorithmException {
        new ImportCitiesRequest().importCities(listCsvCities);
    }

    public Part getFileUpload() {
        return fileUpload;
    }

    public void setFileUpload(Part fileUpload) {
        this.fileUpload = fileUpload;
    }

    public void upload() throws IOException {
        if (!fileUpload.getContentType().equals("text/csv") && !fileUpload.getContentType().equals("application/vnd.ms-excel")) {
            Messages.warn("Validação", "Arquivo inválido. Deve ser enviado o arquivo do tipo CSV!");
            return;
        }
        InputStream input = null;
        OutputStream output = null;

        String resources = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("") + File.separator + "resources" + File.separator + "clients" + File.separator + "xptosystems" + File.separator + "import";
        File fileResources = new File(resources);
        if (!fileResources.exists()) {
            fileResources.mkdirs();
        }
        File file = new File(resources + File.separator + "cities.csv");
        if (file.exists()) {
            file.delete();
        }

        FileUpload.upload("import", "cities.csv", fileUpload);

        if (!file.exists()) {
            return;
        }

        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {

                try {
                    // use comma as separator
                    String[] cities = line.split(cvsSplitBy);

                    Cities c = new Cities();
                    c.setIbge_id(Long.parseLong(cities[0]));
                    c.setUf(cities[1]);
                    c.setName(cities[2]);
                    c.setCapital(Boolean.parseBoolean(cities[3]));
                    c.setLon(Double.parseDouble(cities[4]));
                    c.setLat(Double.parseDouble(cities[5]));
                    c.setNo_accents(cities[6]);
                    c.setAlternative_names(cities[7]);
                    c.setMicro_region(cities[8]);
                    c.setMeso_region(cities[9]);
                    listCsvCities.add(c);
                } catch (Exception e) {

                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("file size: " + fileUpload.getSize());
        System.out.println("file type: " + fileUpload.getContentType());
        System.out.println("file info: " + fileUpload.getHeader("Content-Disposition"));
        fileUpload = null;
    }

    public Boolean getExists() {
        if (fileUpload != null) {
            return true;
        }
        return false;
    }

    public List<Cities> getListCsvCities() {
        return listCsvCities;
    }

    public void setListCsvCities(List<Cities> listCsvCities) {
        this.listCsvCities = listCsvCities;
    }

}
