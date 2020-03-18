/*
 * Copyright (c) 2008-2020, Hazelcast, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hazelcast.jet.sql.imap;

import com.hazelcast.jet.sql.CreateDagVisitor;
import com.hazelcast.jet.sql.PhysicalRel;
import org.apache.calcite.plan.RelOptCluster;
import org.apache.calcite.plan.RelTraitSet;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelWriter;
import org.apache.calcite.rel.core.Project;
import org.apache.calcite.rel.type.RelDataType;
import org.apache.calcite.rex.RexNode;

import java.util.List;

/**
 * Physical projection.
 * <p>
 * Traits:
 * <ul>
 *     <li><b>Collation</b>: propagated from input if prefix of sort fields are still there; destroyed otherwise</li>
 *     <li><b>Distribution</b>: derived from input if all distribution fields are still there; destroyed otherwise</li>
 * </ul>
 */
public class IMapProjectPhysicalRel extends Project implements PhysicalRel {
    public IMapProjectPhysicalRel(
        RelOptCluster cluster,
        RelTraitSet traits,
        RelNode input,
        List<? extends RexNode> projects,
        RelDataType rowType
    ) {
        super(cluster, traits, input, projects, rowType);
    }

    @Override
    public final Project copy(RelTraitSet traitSet, RelNode input, List<RexNode> projects, RelDataType rowType) {
        return new IMapProjectPhysicalRel(getCluster(), traitSet, input, projects, rowType);
    }

    @Override
    public final RelWriter explainTerms(RelWriter pw) {
        return super.explainTerms(pw);
    }

    @Override
    public void visit(CreateDagVisitor visitor) {
        ((PhysicalRel) input).visit(visitor);

        visitor.onProject(this);
    }
}
