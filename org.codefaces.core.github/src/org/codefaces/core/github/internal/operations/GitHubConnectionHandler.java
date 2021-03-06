package org.codefaces.core.github.internal.operations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codefaces.core.connectors.SCMConnector;
import org.codefaces.core.connectors.SCMResponseException;
import org.codefaces.core.connectors.SCMURLException;
import org.codefaces.core.github.internal.connectors.GitHubConnector;
import org.codefaces.core.github.internal.operations.dtos.GitHubRepoDTO;
import org.codefaces.core.github.internal.operations.dtos.GitHubRepoDataDTO;
import org.codefaces.core.models.Repo;
import org.codefaces.core.models.RepoCredential;
import org.codefaces.core.operations.SCMOperationHandler;
import org.codefaces.core.operations.SCMOperationParameter;
import org.codefaces.core.operations.SCMOperationParameters;
import org.eclipse.core.runtime.Assert;

public class GitHubConnectionHandler implements SCMOperationHandler {
	private static final String HTTP_WWW_GITHUB_ORG = "http://www.github.org";

	private static final String HTTP_GITHUB_COM = "http://github.com";

	private static final String OPTIONAL_ENDING_SLASH_PATTERN = "(?:/)?";

	public static final String ID = "org.codefaces.core.operations.SCMOperation.connection";

	private static final String SHOW_GITHUB_REPO = "http://github.com/api/v2/json/repos/show";

	private static final Pattern URL_PATTERN = Pattern.compile("(?:"
			+ Pattern.quote(HTTP_WWW_GITHUB_ORG) + "|"
			+ Pattern.quote(HTTP_GITHUB_COM) + ")/([^/]+)/([^/]+)"
			+ OPTIONAL_ENDING_SLASH_PATTERN);

	@Override
	public Repo execute(SCMConnector connector, SCMOperationParameters parameter) {
		Object urlPara = parameter.getParameter(SCMOperationParameter.URL);
		Assert.isTrue(urlPara instanceof String);

		String url = (String) urlPara;
		Matcher matcher = URL_PATTERN.matcher(url);
		if (matcher.matches()) {
			String owner = matcher.group(1);
			String repoName = matcher.group(2);

			RepoCredential credential = new RepoCredential(null, null);
			Repo repo = new Repo(connector.getKind(), url, repoName, credential);
			repo.setProperty(GitHubOperationConstants.GITHUB_OWNER, owner);

			try {
				String repoInfoUrl = createShowRepoInfoURL(repo);
				// TODO: populate into the repo object
				fetchRepoDataDto((GitHubConnector) connector, repoInfoUrl);
			} catch (Exception e) {
				throw new SCMResponseException("Fail to connect repository: "
						+ url);
			}

			return repo;
		}

		throw new SCMURLException("Invalid GitHub repository url: " + url);
	}

	protected String createShowRepoInfoURL(Repo repo) {
		return GitHubOperationUtil.makeURI(SHOW_GITHUB_REPO, (String) repo
				.getProperty(GitHubOperationConstants.GITHUB_OWNER), repo
				.getName());
	}

	private GitHubRepoDataDTO fetchRepoDataDto(GitHubConnector connector,
			String getGitHubRepoInfoUrl) {
		GitHubRepoDTO gitHubRepoDto = GitHubOperationUtil.fromJson(
				connector.getResponseBody(getGitHubRepoInfoUrl),
				GitHubRepoDTO.class);

		return gitHubRepoDto.getRepository();
	}
}
