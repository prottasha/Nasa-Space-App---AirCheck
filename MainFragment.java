public static SocialNetworkManager mSocialNetworkManager;

  public static final int TWITTER = 1;
  public static final int LINKEDIN = 2;
  public static final int FACEBOOK = 4;

  private Button facebook;
  private Button twitter;
  private Button linkedin;

  public MainFragment() {
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
      View rootView = inflater.inflate(R.layout.main_fragment, container, false);
      ((MainActivity)getActivity()).getSupportActionBar().setTitle(R.string.app_name);
      // init buttons and set Listener
      facebook = (Button) rootView.findViewById(R.id.facebook);
      facebook.setOnClickListener(loginClick);
      twitter = (Button) rootView.findViewById(R.id.twitter);
      twitter.setOnClickListener(loginClick);
      linkedin = (Button) rootView.findViewById(R.id.linkedin);
      linkedin.setOnClickListener(loginClick);

      //Get Keys for initiate SocialNetworks
      String TWITTER_CONSUMER_KEY = getActivity().getString(R.string.twitter_consumer_key);
      String TWITTER_CONSUMER_SECRET = getActivity().getString(R.string.twitter_consumer_secret);
      String LINKEDIN_CONSUMER_KEY = getActivity().getString(R.string.linkedin_consumer_key);
      String LINKEDIN_CONSUMER_SECRET = getActivity().getString(R.string.linkedin_consumer_secret);

      //Chose permissions
      ArrayList<String> fbScope = new ArrayList<String>();
      fbScope.addAll(Arrays.asList("public_profile, email, user_friends"));
      String linkedInScope = "r_basicprofile+rw_nus+r_network+w_messages";

      //Use manager to manage SocialNetworks
      mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(SOCIAL_NETWORK_TAG);

      //Check if manager exist
      if (mSocialNetworkManager == null) {
          mSocialNetworkManager = new SocialNetworkManager();

          //Init and add to manager FacebookSocialNetwork
          FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, fbScope);
          mSocialNetworkManager.addSocialNetwork(fbNetwork);

          //Init and add to manager TwitterSocialNetwork
          TwitterSocialNetwork twNetwork = new TwitterSocialNetwork(this, TWITTER_CONSUMER_KEY, TWITTER_CONSUMER_SECRET);
          mSocialNetworkManager.addSocialNetwork(twNetwork);

          //Init and add to manager LinkedInSocialNetwork
          LinkedInSocialNetwork liNetwork = new LinkedInSocialNetwork(this, LINKEDIN_CONSUMER_KEY, LINKEDIN_CONSUMER_SECRET, linkedInScope);
          mSocialNetworkManager.addSocialNetwork(liNetwork);

          //Initiate every network from mSocialNetworkManager
          getFragmentManager().beginTransaction().add(mSocialNetworkManager, SOCIAL_NETWORK_TAG).commit();
          mSocialNetworkManager.setOnInitializationCompleteListener(this);
      } else {
          //if manager exist - get and setup login only for initialized SocialNetworks
          if(!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
              List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
              for (SocialNetwork socialNetwork : socialNetworks) {
                  socialNetwork.setOnLoginCompleteListener(this);
                  initSocialNetwork(socialNetwork);
              }
          }
      }
      return rootView;
  }

  private void initSocialNetwork(SocialNetwork socialNetwork){
      if(socialNetwork.isConnected()){
          switch (socialNetwork.getID()){
              case FACEBOOK:
                  facebook.setText("Show Facebook profile");
                  break;
              case TWITTER:
                  twitter.setText("Show Twitter profile");
                  break;
              case LINKEDIN:
                  linkedin.setText("Show LinkedIn profile");
                  break;
          }
      }
  }

  @Override
  public void onSocialNetworkManagerInitialized() {
      //when init SocialNetworks - get and setup login only for initialized SocialNetworks
      for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
          socialNetwork.setOnLoginCompleteListener(this);
          initSocialNetwork(socialNetwork);
      }
  }
