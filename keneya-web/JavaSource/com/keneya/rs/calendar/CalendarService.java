package com.keneya.rs.calendar;

import java.util.ArrayList;

import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.keneya.model.calendar.Occurrence;

@Path("/calendar")
@Produces({ "text/xml"})
@ServletSecurity(@HttpConstraint(rolesAllowed="guest"))
public class CalendarService {

//	@Inject
//	private EventManager eventManager;
	
	@GET
	@Path("/view")
	public OccurrenceListResult getMyEvents(@QueryParam("mindate") long minDate,@QueryParam("maxdate") long maxDate){
		// TODO: get user id
//		return eventManager.getOccurencesByReader(0L, new Date(minDate), new Date(maxDate));
		return new OccurrenceListResult(new ArrayList<Occurrence>());
	}

	@GET
	@Path("/view/{calendarId}")
	public OccurrenceListResult getMyEvents(@PathParam("calendarId") long calendarId, @QueryParam("mindate") long minDate,@QueryParam("maxdate") long maxDate){
//		return eventManager.getOccurencesByCalendar(calendarId, new Date(minDate), new Date(maxDate));
		return new OccurrenceListResult(new ArrayList<Occurrence>());
	}
}

