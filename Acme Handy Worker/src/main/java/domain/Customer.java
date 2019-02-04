package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Customer extends Actor {
	
	private Collection<FixUpTask> fixUpTasks;

	@Valid
	@ElementCollection
	@OneToMany(mappedBy="customer")
	public Collection<FixUpTask> getFixUpTasks() {
		return fixUpTasks;
	}

	public void setFixUpTasks(Collection<FixUpTask> fixUpTasks) {
		this.fixUpTasks = fixUpTasks;
	}

	// Control Check ----------------------------------------------------------
	//TODO Control Check Customer 1 -> 0..* Quolet

	private Collection<Quolet> quolets;

	@OneToMany(mappedBy = "customer")
	public Collection<Quolet> getQuolets() {
		return quolets;
	}

	public void setQuolets(Collection<Quolet> quolets) {
		this.quolets = quolets;
	}
}
