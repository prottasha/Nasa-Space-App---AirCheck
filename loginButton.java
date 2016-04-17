private View.OnClickListener loginClick = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          int networkId = 0;
          switch (view.getId()){
              case R.id.facebook:
                  networkId = FACEBOOK;
                  break;
              case R.id.twitter:
                  networkId = TWITTER;
                  break;
              case R.id.linkedin:
                  networkId = LINKEDIN;
                  break;
          }
          SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
          if(!socialNetwork.isConnected()) {
              if(networkId != 0) {
                  socialNetwork.requestLogin();
                  MainActivity.showProgress(socialNetwork, "Loading social person");
              } else {
                  Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
              }
          } else {
              startProfile(socialNetwork.getID());
          }
      }
  };
