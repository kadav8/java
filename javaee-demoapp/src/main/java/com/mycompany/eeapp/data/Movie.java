package com.mycompany.eeapp.data;

import javax.persistence.*;

@Entity
public class Movie extends AbstractEntity {

    private String title;
    @ManyToOne
    private Director director;
    private Integer year;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

	@Override
	public String toString() {
		return "Movie{" +
				"title='" + title + '\'' +
				", director=" + director +
				", year=" + year +
				'}';
	}
}
