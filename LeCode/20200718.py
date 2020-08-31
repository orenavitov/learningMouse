"""
我们有一系列公交路线。每一条路线 routes[i] 上都有一辆公交车在上面循环行驶。例如，有一条路线 routes[0] = [1, 5, 7]，表示第一辆 (下标为0) 公交车会一直按照 1->5->7->1->5->7->1->... 的车站路线行驶。

假设我们从 S 车站开始（初始时不在公交车上），要去往 T 站。 期间仅可乘坐公交车，求出最少乘坐的公交车数量。返回 -1 表示不可能到达终点车站。

链接：https://leetcode-cn.com/problems/bus-routes
"""
class Solution:
    def numBusesToDestinationOther(self, routes, S, T):
        pass

    def numBusesToDestination(self, routes, S, T):
        busStation = self.createBusStation(routes)
        buses_take_able = []
        buses_taked = []

        buses = busStation[S]
        for bus in buses:
            buses_take_able.append(bus)
        min = 1
        while(len(buses_take_able) != 0):
            # stations_visited = []
            temp = len(buses_take_able)
            for i in range(temp):
                bus = buses_take_able[0]
                buses_take_able.remove(bus)
                buses_taked.append(bus)
                route = routes[bus]
                for station in route:
                    # if station not in stations_visited:
                        if station == T:
                            return min
                        # stations_visited.append(station)
                        others_buses = busStation[station]
                        for other_bus in others_buses:
                            if other_bus not in buses_take_able and other_bus not in buses_taked:
                                buses_take_able.append(other_bus)
                # buses_taked.remove(bus)
            min = min + 1
        return -1


    def createBusStation(self, routes):
        busStation = {}
        for bus, route in enumerate(routes):
            for station in route:
                if station not in busStation.keys():
                    busStation.setdefault(station, [])
                    busStation.get(station).append(bus)
                else:
                    if bus not in busStation.get(station):
                        busStation.get(station).append(bus)
        return busStation
routes = [[3,16,33,45,59,79,103,135],[3,35,39,54,56,78,96,101,120,132,146,148],[13,72,98],
          [37,70,107],[0,12,31,37,41,68,78,94,100,101,113,123],[11,32,52,85,135],
          [43,50,128],[0,13,49,51,53,55,60,65,66,80,82,87,92,99,112,118,120,125,128,131,137],[15,19,34,37,45,52,56,97,108,123,142],
          [7,9,20,28,29,33,34,38,43,46,47,48,53,59,65,72,74,80,88,92,110,111,113,119,135,140],[15,41,64,83],[7,13,26,31,57,85,101,108,110,115,119,124,149],
          [47,61,67,70,74,75,77,84,92,101,124,132,133,142,147],[0,2,5,6,12,18,34,37,47,58,77,98,99,109,112,131,135,149],[6,7,8,9,14,17,21,25,33,40,45,50,56,57,58,60,68,92,93,100,108,114,130,149],
          [7],[5,16,22,48,77,82,108,114,124],[34,71],
          [8,16,32,48,104,108,116,134,145],[3,10,16,19,35,45,64,74,89,101,116,149],[1,5,7,10,11,18,40,45,50,51,52,54,55,69,71,81,82,83,85,89,96,100,114,115,124,134,138,148],
          [0,2,3,5,6,9,15,52,64,103,108,114,146],[5,33,39,40,44,45,66,67,68,69,84,102,106,115,120,128,133],[17,26,49,50,55,58,60,65,88,90,102,121,126,130,137,139,144],
          [6,12,13,37,41,42,48,50,51,55,64,65,68,70,73,102,106,108,120,123,126,127,129,135,136,149],[6,7,12,33,37,41,47,53,54,80,107,121,126],[15,75,91,103,107,110,125,139,142,149],
          [18,24,30,52,61,64,75,79,85,95,100,103,105,111,128,129,142],[3,14,18,32,45,52,57,63,68,78,85,91,100,104,111,114,142],[4,7,11,20,21,31,32,33,48,61,62,65,66,73,80,92,93,97,99,108,112,116,136,139]]
S = 85
T = 112
s = Solution()
result = s.numBusesToDestination(routes, S, T)
print(result)
