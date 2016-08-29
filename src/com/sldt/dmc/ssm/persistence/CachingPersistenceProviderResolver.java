package com.sldt.dmc.ssm.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceProviderResolver;

import org.hibernate.ejb.HibernatePersistence;

public class CachingPersistenceProviderResolver implements
		PersistenceProviderResolver {
	
	
	private List<PersistenceProvider> providers = new ArrayList();


	
	@Override
	public void clearCachedProviders() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PersistenceProvider> getPersistenceProviders() {
		// TODO Auto-generated method stub
		System.out.println("..CachingPersistenceProviderResolver..getPersistenceProviders....");
		providers.add(new HibernatePersistence());
		return providers;
	}

}
