package br.com.xptosystems.address;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;

@Entity
@Table(name = "cities",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "uf", "capital"})
)
public class Cities implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ibge_id", unique = true, nullable = false)
    private Long ibge_id;
    @Column(name = "uf", length = 2, nullable = false)
    private String uf;
    @Column(name = "name", length = 150, nullable = false)
    private String name;
    @Column(name = "capital", nullable = false, columnDefinition = "boolean default 0")
    private Boolean capital;
    @Column(name = "lon", nullable = true, precision = 10, scale = 12)
    private Double lon;
    @Column(name = "lat", nullable = true, precision = 10, scale = 12)
    private Double lat;
    @Column(name = "no_accents", length = 150, nullable = false)
    private String no_accents;
    @Column(name = "alternative_accents", length = 150, nullable = false)
    private String alternative_names;
    @Column(name = "micro_region", length = 100, nullable = false)
    private String micro_region;
    @Column(name = "meso_region", length = 100, nullable = false)
    private String meso_region;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    private Date register_date;

    public Cities() {
        this.id = null;
        this.ibge_id = null;
        this.uf = "";
        this.name = "";
        this.capital = false;
        this.lon = null;
        this.lat = null;
        this.no_accents = "";
        this.alternative_names = "";
        this.micro_region = "";
        this.meso_region = "";
        this.register_date = new Date();
    }

    public Cities(Long id, Long ibge_id, String uf, String name, Boolean capital, Double lon, Double lat, String no_accents, String alternative_names, String micro_region, String meso_region, Date register_date) {
        this.id = id;
        this.ibge_id = ibge_id;
        this.uf = uf;
        this.name = name;
        this.capital = capital;
        this.lon = lon;
        this.lat = lat;
        this.no_accents = no_accents;
        this.alternative_names = alternative_names;
        this.micro_region = micro_region;
        this.meso_region = meso_region;
        this.register_date = register_date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIbge_id() {
        return ibge_id;
    }

    public void setIbge_id(Long ibge_id) {
        this.ibge_id = ibge_id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getCapital() {
        return capital;
    }

    public void setCapital(Boolean capital) {
        this.capital = capital;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getNo_accents() {
        return no_accents;
    }

    public void setNo_accents(String no_accents) {
        this.no_accents = no_accents;
    }

    public String getAlternative_names() {
        return alternative_names;
    }

    public void setAlternative_names(String alternative_names) {
        this.alternative_names = alternative_names;
    }

    public String getMicro_region() {
        return micro_region;
    }

    public void setMicro_region(String micro_region) {
        this.micro_region = micro_region;
    }

    public String getMeso_region() {
        return meso_region;
    }

    public void setMeso_region(String meso_region) {
        this.meso_region = meso_region;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

}
