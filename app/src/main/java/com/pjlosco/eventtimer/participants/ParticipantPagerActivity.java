package com.pjlosco.eventtimer.participants;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.pjlosco.eventtimer.R;

import java.util.ArrayList;
import java.util.UUID;

public class ParticipantPagerActivity extends FragmentActivity {
    private ViewPager viewPager;
    private ArrayList<Participant> participants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.viewPager);
        setContentView(viewPager);

        participants = ParticipantCatalogue.get(this).getParticipants();

        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public android.app.Fragment getItem(int position) {
                Participant participant = participants.get(position);
                return ParticipantFragment.newInstance(participant.getId());
            }

            @Override
            public int getCount() {
                return participants.size();
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Participant participant = participants.get(position);
                try {
                    setTitle(participant.getFirstName() + participant.getLastName());
                } catch (NullPointerException e) {
                    setTitle("Un-named Participant");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        UUID participantId = (UUID)getIntent().getSerializableExtra(ParticipantFragment.EXTRA_PARTICIPANT_ID);
        for (int index = 0; index < participants.size(); index++) {
            if (participants.get(index).getId().equals(participantId)) {
                viewPager.setCurrentItem(index);
                break;
            }
        }
    }

}
