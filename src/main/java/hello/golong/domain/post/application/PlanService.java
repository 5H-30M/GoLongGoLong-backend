package hello.golong.domain.post.application;

import hello.golong.domain.post.dao.PlanRepository;
import hello.golong.domain.post.domain.Plan;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PlanService {

    private final PlanRepository planRepository;

    @Autowired
    public PlanService(PlanRepository planRepository) {
        this.planRepository = planRepository;
    }

    public void savePlans(Long post_id, Long type, Map<String, Long> plans) {

        for(String key : plans.keySet()) {
            Plan plan = Plan.builder()
                    .postId(post_id)
                    .content(key)
                    .amount(plans.get(key))
                    .type(type)
                    .build();

            planRepository.save(plan);
        }
    }

    public Map<String, Long> findPlans(Long post_id, Long type) {

        Optional<List<Plan>> optionalPlans = planRepository.findByPostIdAndType(post_id, type);
        Map<String, Long> map = new HashMap<>();

        optionalPlans.ifPresent( plans -> {
            for(Plan plan : plans) {
                map.put(plan.getContent(), plan.getAmount());
            }
        });
        return map;
    }

    public void deletePlans(Long post_id, Long type) {

        Optional<List<Plan>> optionalPlans = planRepository.findByPostIdAndType(post_id, type);

        optionalPlans.ifPresent( plans -> {
            for(Plan plan : plans) {
                planRepository.delete(plan);
            }
        });

    }
}
