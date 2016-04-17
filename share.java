private View.OnClickListener shareClick = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
          AlertDialog.Builder ad = alertDialogInit("Would you like to post Link:", link);
          ad.setPositiveButton("Post link", new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                  if(networkId != MainFragment.TWITTER){
                      Bundle postParams = new Bundle();
                      postParams.putString(SocialNetwork.BUNDLE_LINK, link);
                      socialNetwork.requestPostLink(postParams, message, postingComplete);
                  } else {
                      socialNetwork.requestPostMessage(message + " " + link, postingComplete);
                  }
              }
          });
          ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override
              public void onClick(DialogInterface dialog, int i) {
                  dialog.cancel();
              }
          });
          ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
              public void onCancel(DialogInterface dialog) {
                  dialog.cancel();
              }
          });
          ad.create().show();
      }
  };

  private AlertDialog.Builder alertDialogInit(String title, String message){
      AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
      ad.setTitle(title);
      ad.setMessage(message);
      ad.setCancelable(true);
      return ad;
  }
