package APITest;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.Base.BaseAPI;
import com.Base.Log;

public class GitRepoTest extends BaseAPI {

	String bSlash = "/";
	String[] getName = { "name", "commit.sha", "commit.url" };
	String getAuthor = "commit.commit.author.name";
	String sName;

	@DataProvider

	public String[] FetchBranchdetails() throws Exception {

		String[] testObjArray = getName;
		return (testObjArray);

	}

	@Test(dataProvider = "FetchBranchdetails", priority = 0)
	public void fetchBranchData(String getPath) {

		String branchData = getBranches(getPath);

		Log.info("list of branch data:--> " + branchData);

	}

	@Test(priority = 1)
	public void fetchBranchName() {

		sName = getBranches(getName[0]);

		Log.info("Branch name is: " + sName);

	}

	@Test(dependsOnMethods = "fetchBranchName")
	public void fetchBranchByName() {

		String js = getBranches(bSlash + sName, getAuthor);

		Log.info("Author is: " + js);

	}

}
