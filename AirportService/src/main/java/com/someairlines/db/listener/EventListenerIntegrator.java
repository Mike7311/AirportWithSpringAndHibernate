package com.someairlines.db.listener;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;

public class EventListenerIntegrator implements Integrator {

	@Override
	public void disintegrate(SessionFactoryImplementor arg0, SessionFactoryServiceRegistry arg1) {
	}

	@SuppressWarnings("unchecked")
	@Override
	public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactoryImplementor, 
			SessionFactoryServiceRegistry sessionFactoryServiceRegistry) {
		EventListenerRegistry eventListenerRegistry = 
	            sessionFactoryServiceRegistry.getService(EventListenerRegistry.class);
		eventListenerRegistry.appendListeners(EventType.PRE_DELETE, PreDeleteEventListenerImpl.class);
		eventListenerRegistry.appendListeners(EventType.PRE_UPDATE, PreUpdateEventListenerImpl.class);
	}

}
