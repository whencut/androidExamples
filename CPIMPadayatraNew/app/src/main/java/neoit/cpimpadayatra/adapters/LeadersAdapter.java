package neoit.cpimpadayatra.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import neoit.cpimpadayatra.R;
import java.util.HashMap;
import java.util.List;

public class LeadersAdapter extends RecyclerView.Adapter<LeadersAdapter.MemberViewHolder>{

    private List<HashMap<String,String>> members;
    private Context context;

    public LeadersAdapter(List<HashMap<String,String>> members, Context context) {
        this.members = members;
        this.context = context;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private TextView name_tv;
        private TextView district_tv;
        private TextView member_phone_emailid;

        public MemberViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name_tv = (TextView) itemView.findViewById(R.id.member_name);
            district_tv = (TextView) itemView.findViewById(R.id.member_district);
            member_phone_emailid= (TextView) itemView.findViewById(R.id.member_phone_emailid);
        }
    }

    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_leaders, viewGroup, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MemberViewHolder memberViewHolder, int i) {
        HashMap<String,String> hm=members.get(i);
        memberViewHolder.name_tv.setText(hm.get("name"));
        memberViewHolder.member_phone_emailid.setText(hm.get("phone_number")+" - "+hm.get("emailid"));
        memberViewHolder.district_tv.setText(hm.get("village")+", "+hm.get("mandal")+", "+hm.get("district"));

        memberViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return members.size();
    }

}
