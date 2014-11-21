package conference.manager.model.domain;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import conference.manager.model.ModelException;
import conference.manager.model.database.ModelDatabase;

public class ConferenceTest {
	Conference scoredConference;
	Conference unscoredConference;
	Conference unallocatedConference;
	List<Article> acceptedArticles;
	List<Article> rejectedArticles;
	List<Article> articles;
	List<Researcher> reviewers;

	@Before
	public void setUp() throws Exception {
		ModelDatabase database = new ModelDatabase(true);
		scoredConference = database.getAllConferences().get(1);
		unscoredConference = database.getAllConferences().get(0);
		unallocatedConference = database.getAllConferences().get(2);
		reviewers = database.getAllResearchers().subList(0, 7);
		articles = database.getAllArticles().subList(1,6);
		acceptedArticles = articles.subList(0, 3);
		rejectedArticles = articles.subList(3, 5);

	}

	@Test
	public void getNameTest() {
		assertEquals("ICSE", scoredConference.getName());
		assertEquals("FSE", unscoredConference.getName());
		assertEquals("SBES", unallocatedConference.getName());
	}

	@Test
	public void getArticlesTest() {
		assertEquals(articles, scoredConference.getArticles());
	}

	@Test
	public void getReviewersTest() {
		assertEquals(reviewers, scoredConference.getReviewers());
	}

	@Test
	public void getAcceptedArticlesSuccessTest() {
		try {
			scoredConference.selectArticles();
			assertEquals(acceptedArticles,
					scoredConference.getAcceptedArticles());
		} catch (ModelException e) {
			fail();
		}
	}

	@Test(expected = ModelException.class)
	public void getAcceptedArticlesFailTest() throws ModelException {
		scoredConference.getAcceptedArticles();
	}

	@Test
	public void getRejectedArticlesSuccessTest() {
		try {
			scoredConference.selectArticles();
			assertEquals(rejectedArticles,
					scoredConference.getRejectedArticles());
		} catch (ModelException e) {
			fail();
		}
	}

	@Test(expected = ModelException.class)
	public void getRejectedArticlesFailTest() throws ModelException {
		scoredConference.getRejectedArticles();
	}

	@Test
	public void selectArticlesSuccessTest(){
		try {
			scoredConference.selectArticles();
		} catch (ModelException e) {
			fail();
		}
	}

	@Test(expected = ModelException.class)
	public void selectArticlesFailTest() throws ModelException {
		unscoredConference.selectArticles();
	}

	@Test
	public void isScoredTest() {
		assertTrue(scoredConference.isScored());
		assertFalse(unscoredConference.isScored());
		assertFalse(unallocatedConference.isScored());
	}

	@Test
	public void isAllocatedTest() {
		assertTrue(scoredConference.isAllocated());
		assertTrue(unscoredConference.isAllocated());
		assertFalse(unallocatedConference.isAllocated());
	}

}
