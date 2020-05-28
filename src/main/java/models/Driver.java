package models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "drivers")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("Id")
    private int id;

    @Column(name = "name")
    @JsonProperty("Name")
    private String name;

    @Column(name = "age")
    @JsonProperty("Age")
    private int age;

    @Column(name = "experience")
    @JsonProperty("Experience")
    private int experience;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonProperty("Autos")
    private List<Auto> autos;

    public Driver() {

    }

    public Driver(String name, int age, int experience) {
        this.name = name;
        this.age = age;
        this.experience = experience;
        autos = new ArrayList<Auto>();
    }

    public void addAuto(Auto auto) {
        auto.setDriver(this);
        autos.add(auto);
    }

    public void removeAuto(Auto auto) {
        autos.remove(auto);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public List<Auto> getAutos() {
        return autos;
    }

    public void setAutos(List<Auto> autos) {
        this.autos = autos;
    }
}
