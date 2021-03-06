package org.codefaces.web.urls.github;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.codefaces.ui.SCMConfigurableElement;
import org.codefaces.ui.SCMURLConfiguration;
import org.codefaces.web.urls.URLParsingStrategy;

public class GitHubUrlParseStrategy implements URLParsingStrategy {
	// TODO use GitHubConstants
	private static final String BRANCHES_FOLDER_NAME = "branches";
	private static final String MASTER_BRANCH = BRANCHES_FOLDER_NAME
			+ "/master";

	private static final String SCM_KIND = "GitHub";

	private static final String HTTP_PREFIX = "http://";

	private static final String HTTPS_PREFIX = "https://";

	private static final String GITHUB_COM = "github.com";

	private static final String OPTIONAL_ENDING_SLASH_PATTERN = "(?:/)?";

	private static final Pattern URL_PATTERN = Pattern.compile("((?:"
			+ Pattern.quote(HTTP_PREFIX + GITHUB_COM) + "|"
			+ Pattern.quote(HTTPS_PREFIX + GITHUB_COM)
			+ ")/[^/]+/[^/]+)(?:/[^/]+/([^/]+).*)?"
			+ OPTIONAL_ENDING_SLASH_PATTERN);

	@Override
	public boolean canParse(String url) {
		return URL_PATTERN.matcher(url).matches();
	}

	@Override
	public SCMURLConfiguration buildConfigurations(String url) {
		SCMURLConfiguration config = new SCMURLConfiguration();

		Matcher matcher = URL_PATTERN.matcher(url);
		if (matcher.matches()) {
			String repoUrl = matcher.group(1);
			String branchName = matcher.group(2);

			if (repoUrl != null) {
				config.put(SCMConfigurableElement.REPO_URL, repoUrl);
			}

			if (branchName != null) {
				config.put(SCMConfigurableElement.BASE_DIRECTORY,
						BRANCHES_FOLDER_NAME + "/" + branchName);
			} else {
				config.put(SCMConfigurableElement.BASE_DIRECTORY, MASTER_BRANCH);
			}

			config.put(SCMConfigurableElement.SCM_KIND, SCM_KIND);
		}

		return config;
	}
}
