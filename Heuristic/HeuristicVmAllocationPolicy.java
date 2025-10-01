package org.cloudbus.cloudsim.examples;

import java.util.List;
import java.util.Map;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;

/**
 * A VM Allocation Policy that places a VM on the Host with the lowest CPU utilization.
 */
public class HeuristicVmAllocationPolicy extends VmAllocationPolicySimple {

    public HeuristicVmAllocationPolicy(List<? extends Host> list) {
        super(list);
    }

    /**
     * Finds the best host for a given VM.
     * This method is overridden to implement our custom heuristic.
     * @param vm The VM to be placed.
     * @return The host that has been chosen, or null if no suitable host is found.
     */
    @Override
    public Host findHostForVm(Vm vm) {
        Host bestHost = null;
        double minUtilization = Double.MAX_VALUE;

        // Loop through all available hosts
        for (Host host : getHostList()) {
            // Check if the host has enough resources for the VM
            if (host.isSuitableForVm(vm)) {
                
                
                // Calculate utilization by checking how many MIPS are in use
                double utilization = (host.getTotalMips() - host.getVmScheduler().getAvailableMips()) / host.getTotalMips();

                // If this host is less busy than the best one found so far, it's the new best
                if (utilization < minUtilization) {
                    minUtilization = utilization;
                    bestHost = host;
                }
            }
        }
        return bestHost;
    }
}