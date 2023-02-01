package com.clone.twitter.service.implementation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.clone.twitter.entity.Hashtags;
import com.clone.twitter.entity.Posts;
import com.clone.twitter.model.HashtagModel;
import com.clone.twitter.model.PostModel;
import com.clone.twitter.repository.HashtagPostsRepository;
import com.clone.twitter.repository.HashtagsRepository;
import com.clone.twitter.service.HashtagService;
import com.clone.twitter.utility.Mapper;

@Service
public class HashtagServiceImpl implements HashtagService {

	@Autowired private HashtagsRepository hashtagsRepository;

	@Autowired private HashtagPostsRepository hashtagPostsRepository;

	@Autowired
	@Qualifier("HashtagMapper")
	private Mapper<Hashtags, HashtagModel> hashtagMapper;

	@Autowired
	@Qualifier("PostMapper")
	private Mapper<Posts, PostModel> postMapper;

	@Override
	public List<HashtagModel> getHashtags() {

		var hashtags = hashtagsRepository.findAll();
		List<HashtagModel> hashtagModels = new ArrayList<>();
		Optional.ofNullable(hashtags)
		.ifPresent(
				hashtag -> hashtag.forEach(hTag -> hashtagModels.add(hashtagMapper.transform(hTag))));
		return hashtagModels;
	} // TODO: Create a stored procedure in DB.

	@Override
	public List<PostModel> getPosts(String tag) {

		var hashtag = hashtagsRepository.findByTag(tag);
		List<Posts> posts = new ArrayList<>();
		if (null != hashtag) {
			posts = hashtagPostsRepository.findByHashtags(hashtag);
		}
		List<PostModel> postModels = new ArrayList<>();
		Optional.ofNullable(posts)
		.ifPresent(post -> post.forEach(pst -> postModels.add(postMapper.transform(pst))));
		return postModels;
	}

	@Override
	@Transactional
	public List<Hashtags> getHashtagsByTags(List<String> tags) {

		List<Hashtags> outputListOfHashtags = new ArrayList<>();
		List<Hashtags> hashTags = hashtagsRepository.findByTagIn(tags);
		Set<String> existingTags = fetchExistingTags(hashTags);
		Set<String> allTags = new HashSet<>(tags);
		allTags.removeAll(existingTags);
		setHashTagCount(hashTags);
		List<Hashtags> toBeCreatedHashTags = new ArrayList<>(hashTags);
		Optional.ofNullable(allTags)
		.ifPresent(
				notPresentTags -> {
					notPresentTags.forEach(
							notPresentTag -> {
								Hashtags newHashtag = new Hashtags();
								newHashtag.setTag(notPresentTag);
								newHashtag.setRecentPostCount(1L);
								toBeCreatedHashTags.add(newHashtag);
							});
				});

		outputListOfHashtags.addAll(hashtagsRepository.saveAll(toBeCreatedHashTags));
		return outputListOfHashtags;
	}

	private void setHashTagCount(List<Hashtags> hashTags) {
		if (!CollectionUtils.isEmpty(hashTags)) {
			hashTags.forEach(
					presentHashTag ->
					presentHashTag.setRecentPostCount(presentHashTag.getRecentPostCount() + 1));
		}
	}

	private Set<String> fetchExistingTags(List<Hashtags> hashTags) {
		if (!CollectionUtils.isEmpty(hashTags)) {
			return hashTags.stream().map(hTag -> hTag.getTag()).collect(Collectors.toSet());
		}
		return new HashSet<>();
	}
}
