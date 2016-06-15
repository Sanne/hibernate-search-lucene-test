/*
 * Hibernate Search, full-text search for your domain model
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.search.tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.test.SearchTestBase;
import org.hibernate.search.testsupport.TestForIssue;
import org.junit.Test;

public class YourTestCase extends SearchTestBase {

	@Override
	public Class<?>[] getAnnotatedClasses() {
		return new Class<?>[]{ YourAnnotatedEntity.class };
	}

	@Test
	@TestForIssue(jiraKey = "HSEARCH-NNNNN") // Please fill in the JIRA key of your issue
	@SuppressWarnings("unchecked")
	public void testYourBug() {
		try ( Session s = openSession() ) {

			Transaction tx = s.beginTransaction();
			s.persist( new YourAnnotatedEntity( 1L, "example" ) );
			s.persist( new YourAnnotatedEntity( 2L, "test" ) );
			tx.commit();

			FullTextSession session = Search.getFullTextSession( s );
			QueryBuilder qb = session.getSearchFactory().buildQueryBuilder().forEntity( YourAnnotatedEntity.class ).get();
			Query query = qb.keyword().onField( "name" ).matching( "example" ).createQuery();

			List<YourAnnotatedEntity> result = session.createFullTextQuery( query ).list();
			assertEquals( 1, result.size() );
			assertEquals( 1l, (long) result.get( 0 ).getId() );

		}
	}

}
