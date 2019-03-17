package br.univates.mapspoints.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.univates.mapspoints.R;
import br.univates.mapspoints.classes.Route;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.MyViewHolder>  {

    public List<Route> routes;
    private Route itemClicked;
    private Runnable run;

    public RouteAdapter() {
        this.routes = new ArrayList<>();
    }

    public void setClickRun(Runnable onClickAction) {
        run = onClickAction;
    }

    public Route getItemClicked() {
        return itemClicked;
    }

    @NonNull
    @Override
    public RouteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemLista = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_route_item, viewGroup, false);
        return new RouteAdapter.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull final RouteAdapter.MyViewHolder myViewHolder, int i) {
        final Route route = routes.get(i);

        myViewHolder.tv_route_item_city.setText(route.getCity());
        myViewHolder.tv_route_item_nickname.setText(route.getNickname());

        myViewHolder.cl_route_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClicked = route;
                if (run != null) {
                    run.run();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return routes.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout cl_route_item;
        TextView tv_route_item_city, tv_route_item_nickname;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cl_route_item = itemView.findViewById(R.id.cl_route_item);
            tv_route_item_city = itemView.findViewById(R.id.tv_route_item_city);
            tv_route_item_nickname = itemView.findViewById(R.id.tv_route_item_nickname);
        }
    }
}
